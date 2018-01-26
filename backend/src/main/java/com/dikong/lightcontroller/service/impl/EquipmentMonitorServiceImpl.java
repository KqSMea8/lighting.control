package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.EquipmentMonitorDao;
import com.dikong.lightcontroller.entity.EquipmentMonitor;
import com.dikong.lightcontroller.service.EquipmentMonitorService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;

import tk.mybatis.mapper.entity.Example;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
@Service
public class EquipmentMonitorServiceImpl implements EquipmentMonitorService {

    @Autowired
    private EquipmentMonitorDao monitorDao;

    @Override
    public ReturnInfo add(EquipmentMonitor equipmentMonitor) {
        equipmentMonitor.setPanelId(AuthCurrentUser.getCurrentProjectId());
        equipmentMonitor.setCreateBy(AuthCurrentUser.getUserId());
        equipmentMonitor.setProjectId(AuthCurrentUser.getCurrentProjectId());
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
        // 获取发送命令相关信息调用相关方法
        int sourceId = monitor.getSourceId();
        if (monitor.getMonitorType() == 1) {
            int dtuId = monitor.getPanelId();
            // 查询 var 类型 如果是开关量则,根据类型,转换value为0,1,或者bigdecimal

            // 发送命令
        } else if (monitor.getMonitorType() == 2) {// 自定义监控
            int sourceType = monitor.getSourceType();// 1->单个设备 2->群组 3->时序
            switch (sourceType) {
                case 1:

                    break;
                case 2:
                    // 根据groupId查询其下所有设备和变量信息
                    // 修改变量为value值
                    // 发送命令
                    break;
                case 3:
                    // 查询时序信息和ID
                    // 如果是群组，逻辑同2
                    // 如果是单个变量，逻辑通1
                    break;

                default:
                    break;
            }
        }
        return null;
    }
}
