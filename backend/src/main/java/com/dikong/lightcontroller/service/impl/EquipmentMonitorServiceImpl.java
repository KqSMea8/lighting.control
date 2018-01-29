package com.dikong.lightcontroller.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.EquipmentMonitorDao;
import com.dikong.lightcontroller.dao.GroupDeviceMiddleDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.entity.EquipmentMonitor;
import com.dikong.lightcontroller.entity.GroupDeviceMiddle;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.service.EquipmentMonitorService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

import tk.mybatis.mapper.entity.Example;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
@Service
public class EquipmentMonitorServiceImpl implements EquipmentMonitorService {

    @Autowired
    private EquipmentMonitorDao monitorDao;

    @Autowired
    private CmdService cmdService;

    @Autowired
    private RegisterDAO registerDao;

    @Autowired
    private TimingDAO timingDao;

    @Autowired
    private GroupDeviceMiddleDAO groupDeviceMiddleDao;

    @Override
    public ReturnInfo add(EquipmentMonitor equipmentMonitor) {
        equipmentMonitor.setPanelId(AuthCurrentUser.getCurrentProjectId());
        equipmentMonitor.setCreateBy(AuthCurrentUser.getUserId());
        equipmentMonitor.setProjectId(AuthCurrentUser.getCurrentProjectId());
        if (equipmentMonitor.getMonitorType() == 1) {
            if (isSwitch(equipmentMonitor.getValueType())) {
                String value = cmdService.readOneAnalog(equipmentMonitor.getSourceId());
                equipmentMonitor.setCurrentValue(new BigDecimal(String.valueOf(new BigDecimal(value)
                        .multiply(equipmentMonitor.getFactor()).doubleValue())));
            } else {
                String value = cmdService.readOneSwitch(equipmentMonitor.getSourceId());
                equipmentMonitor.setCurrentValue(new BigDecimal(value));
            }
        }
        monitorDao.insertSelective(equipmentMonitor);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo del(Integer monitorId) {
        EquipmentMonitor equipmentMonitor = new EquipmentMonitor();
        equipmentMonitor.setMonitorId(monitorId);
        equipmentMonitor.setUpdateBy(AuthCurrentUser.getUserId());
        equipmentMonitor.setIsDelete(2);
        monitorDao.updateByPrimaryKeySelective(equipmentMonitor);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo list(Integer type, Integer id) {
        Example example = new Example(EquipmentMonitor.class);
        example.createCriteria().andEqualTo("isDelete", 1).andEqualTo("monitorType", type)
                .andEqualTo("projectId", AuthCurrentUser.getCurrentProjectId())
                .andEqualTo("panelId", id);
        List<EquipmentMonitor> monitors = monitorDao.selectByExample(example);
        return ReturnInfo.createReturnSuccessOne(monitors);
    }

    @Override
    public ReturnInfo update(EquipmentMonitor equipmentMonitor) {
        equipmentMonitor.setUpdateBy(AuthCurrentUser.getUserId());
        monitorDao.updateByPrimaryKeySelective(equipmentMonitor);
        return ReturnInfo.createReturnSuccessOne(null);
    }


    @Override
    public ReturnInfo chageStatus(Integer monitorId, String value) {
        EquipmentMonitor monitor = monitorDao.selectByPrimaryKey(monitorId);
        int[] sendResult = new int[2];
        // 获取发送命令相关信息调用相关方法
        int sourceId = monitor.getSourceId();
        if (monitor.getMonitorType() == 1) {// 设备监控
            sendCmd(monitorId, (long) sourceId, Integer.valueOf(value), sendResult);
        } else if (monitor.getMonitorType() == 2) {// 自定义监控
            int sourceType = monitor.getSourceType();// 1->单个设备 2->群组 3->时序
            switch (sourceType) {
                case 1:
                    sendCmd(monitorId, (long) sourceId, Integer.valueOf(value), sendResult);
                    break;
                case 2:
                    // 根据groupId查询其下所有设备和变量信息
                    // 修改变量为value值
                    // 发送命令
                    List<GroupDeviceMiddle> middles =
                            groupDeviceMiddleDao.selectByGroupId((long) sourceId);
                    for (GroupDeviceMiddle middle : middles) {
                        sendCmd(monitorId, middle.getRegisId(), Integer.valueOf(value), sendResult);
                    }
                    break;
                case 3:
                    // 查询时序信息和ID
                    Timing timing = timingDao.selectByPrimaryKey(sourceId);
                    if (timing.getRunType() == 1) {// 群组
                        long groupId = timing.getRunVar();// 群组id
                        List<GroupDeviceMiddle> middles2 =
                                groupDeviceMiddleDao.selectByGroupId(groupId);
                        for (GroupDeviceMiddle middle : middles2) {
                            sendCmd(monitorId, middle.getRegisId(), Integer.valueOf(value),
                                    sendResult);
                        }
                    } else {// 设备
                        long varId = timing.getRunId();// 变量id
                        sendCmd(monitorId, varId, Integer.valueOf(value), sendResult);
                    }
                    break;

                default:
                    break;
            }
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("success", String.valueOf(sendResult[0]));
        result.put("fail", String.valueOf(sendResult[1]));
        return ReturnInfo.createReturnSuccessOne(result);
    }

    boolean sendCmd(int monitorId, long sourceId, int value, int[] sendResult) {
        Register register = registerDao.selectRegisById(sourceId);
        // 查询 var 类型 如果是开关量则,根据类型,转换value为0,1,或者bigdecimal
        boolean result = false;
        if (isSwitch(register.getRegisType())) {
            result = cmdService.writeSwitch(register.getId(), SwitchEnum.getByCode(value));
        } else {
            result = cmdService.writeAnalog(register.getId(), value);
        }
        if (result) {
            // 成功
            EquipmentMonitor monitor = new EquipmentMonitor();
            monitor.setMonitorId(monitorId);
            monitor.setCurrentValue(new BigDecimal(value));
            monitorDao.updateByPrimaryKeySelective(monitor);
            sendResult[0]++;
            return true;
        } else {
            sendResult[1]++;
            return false;
        }
    }

    boolean isSwitch(String valueType) {
        if (valueType.equals(Register.AV) || valueType.equals(Register.AI)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ReturnInfo refreshStatus(Integer type, Integer panelId) {
        // TODO Auto-generated method stub
        return null;
    }
}
