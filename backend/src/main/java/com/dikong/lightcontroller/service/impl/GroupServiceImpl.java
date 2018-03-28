package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.BussinessCode;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.GroupDAO;
import com.dikong.lightcontroller.dao.GroupDeviceMiddleDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dao.SysVarDAO;
import com.dikong.lightcontroller.dao.TimingCronDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.dto.CmdSendDto;
import com.dikong.lightcontroller.dto.DeviceDtu;
import com.dikong.lightcontroller.dto.QuartzJobDto;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.entity.Group;
import com.dikong.lightcontroller.entity.GroupDeviceMiddle;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.entity.TimingCron;
import com.dikong.lightcontroller.service.EquipmentMonitorService;
import com.dikong.lightcontroller.service.GroupService;
import com.dikong.lightcontroller.service.SysVarService;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.service.TimingService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.vo.CommandSend;
import com.dikong.lightcontroller.vo.GroupDeviceList;
import com.dikong.lightcontroller.vo.GroupList;
import com.github.pagehelper.PageHelper;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日下午3:28
 * @see
 *      </P>
 */
@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger LOG = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private GroupDeviceMiddleDAO groupDeviceMiddleDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private RegisterDAO registerDAO;

    @Autowired
    private SysVarDAO sysVarDAO;

    @Autowired
    private TimingDAO timingDAO;

    @Autowired
    private TimingCronDAO timingCronDAO;

    @Autowired
    private SysVarService sysVarService;

    @Autowired
    private TimingService timingService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EquipmentMonitorService equipmentMonitorService;

    @Override
    public ReturnInfo<List<Group>> list(GroupList groupList) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        groupList.setProjId(projId);
        groupList.setIsDelete(Group.DEL_NO);
        PageHelper.startPage(groupList.getPageNo(), groupList.getPageSize());
        List<Group> groups = groupDAO.selectAllGroup(groupList);
        return ReturnInfo.createReturnSucces(groups);
    }

    @Override
    @Transactional
    public ReturnInfo add(Group group) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        int existGroupName =
                groupDAO.selectByNameAndProj(projId, group.getGroupName(), Group.DEL_NO);
        if (existGroupName > 0) {
            return ReturnInfo.create(BussinessCode.GROUP_EXIST.getCode(),
                    BussinessCode.GROUP_EXIST.getMsg());
        }
        Integer lastGroupCode = groupDAO.selectLastCode(Group.DEL_NO, projId);
        if (null == lastGroupCode) {
            lastGroupCode = 0;
        }
        lastGroupCode += 1;
        group.setGroupCode(String.valueOf(lastGroupCode));
        group.setProjId(projId);
        group.setCreateBy(AuthCurrentUser.getUserId());
        groupDAO.addGroup(group);
        // 添加群组变量
        BaseSysVar sysVar = new BaseSysVar();
        sysVar.setSysVarType(BaseSysVar.GROUP);
        sysVar.setVarValue(BaseSysVar.CLOSE_SYS_VALUE);
        sysVar.setVarName(group.getGroupName());
        sysVar.setVarId(group.getId());
        sysVar.setProjId(projId);
        sysVarService.addSysVar(sysVar);
        return ReturnInfo.create(group.getId());
    }

    @Override
    public ReturnInfo deleteGroup(Long id) {
        groupDAO.updateIsDelete(id, AuthCurrentUser.getUserId(), Group.DEL_YES);
        groupDeviceMiddleDAO.deleteByGroupId(id);

        sysVarService.deleteSysVar(id, BaseSysVar.GROUP);
        timingService.deleteNodeByGroupId(id);

        equipmentMonitorService.delByGroupId(id);

        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo deleteGroupByDeviceId(Long deviceId) {
        List<Long> groupIds = groupDeviceMiddleDAO.selectAllByDeviceId(deviceId);
        if (!CollectionUtils.isEmpty(groupIds)) {
            groupIds.forEach(item -> groupDeviceMiddleDAO.deleteByGroupId(item));
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo<List<GroupDeviceList>> deviceList(Long id) {

        List<GroupDeviceMiddle> groupDeviceMiddles = groupDeviceMiddleDAO.selectByGroupId(id);
        Set<Long> deviceIds = new HashSet<>();
        Set<Long> regisIds = new HashSet<>();
        if (!CollectionUtils.isEmpty(groupDeviceMiddles)) {
            groupDeviceMiddles.forEach(item -> {
                deviceIds.add(item.getDeviceId());
                regisIds.add(item.getRegisId());
            });
        }
        Map<Long, DeviceDtu> deviceDtuMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(deviceIds)) {
            List<Long> deviceList = new ArrayList<>();
            deviceList.addAll(deviceIds);
            List<DeviceDtu> deviceDtus = deviceDAO.selectByDeviceId(deviceList);
            deviceDtus.forEach(item -> deviceDtuMap.put(item.getId(), item));
        }
        Map<Long, Register> registerMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(regisIds)) {
            List<Long> regisId = new ArrayList<>();
            regisId.addAll(regisIds);
            List<Register> registers = registerDAO.selectRegisterInId(regisId);
            registers.forEach(item -> registerMap.put(item.getId(), item));
        }
        List<GroupDeviceList> groupDeviceLists = new ArrayList<>();
        if (!CollectionUtils.isEmpty(groupDeviceMiddles)) {
            groupDeviceMiddles.forEach(item -> {
                GroupDeviceList groupDeviceList = new GroupDeviceList();
                groupDeviceList.setId(item.getId());
                DeviceDtu deviceDtu = deviceDtuMap.get(item.getDeviceId());
                if (null != deviceDtu) {
                    String devicePlace = deviceDtu.getDtuName() + ":ID"
                            + Integer.parseInt(deviceDtu.getDeviceCode());
                    groupDeviceList.setDevicePlace(devicePlace);
                    groupDeviceList.setExternalId(deviceDtu.getExternalId());
                }
                Register register = registerMap.get(item.getRegisId());
                if (null != register) {
                    groupDeviceList.setRegisAddr(register.getRegisAddr());
                    groupDeviceList.setRegisName(register.getRegisName());
                    groupDeviceList.setRegisType(register.getRegisType());
                    groupDeviceList.setVarName(register.getVarName());
                }
                groupDeviceList.setDeviceId(item.getDeviceId());
                groupDeviceList.setRegisId(item.getRegisId());
                groupDeviceLists.add(groupDeviceList);
            });
        }
        return ReturnInfo.createReturnSuccessOne(groupDeviceLists);
    }

    @Override
    public ReturnInfo deleteGroupDevice(List<Long> middId) {
        middId.forEach(item -> {
            groupDeviceMiddleDAO.deleteDeviceById(item);
        });
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    @Transactional
    @SuppressWarnings("all")
    public ReturnInfo addGroupDevice(GroupDeviceMiddle groupDeviceMiddle) throws Exception {
        Register register = registerDAO.selectRegisById(groupDeviceMiddle.getRegisId());
        if (null != register && (Register.AI.equals(register.getRegisType())
                || Register.AV.equals(register.getRegisType()))) {
            return ReturnInfo.create(BussinessCode.NOADD_SIMULATION.getCode(),
                    BussinessCode.NOADD_SIMULATION.getMsg());
        }
        groupDeviceMiddleDAO.insert(groupDeviceMiddle);
        // 添加到启动的时序变量中
        int projId = AuthCurrentUser.getCurrentProjectId();
        SysVar sysVar = sysVarDAO.selectSequence(projId, BaseSysVar.SEQUENCE);
        if (sysVar == null
                || (sysVar != null && BaseSysVar.CLOSE_SYS_VALUE.equals(sysVar.getVarValue()))) {
            return ReturnInfo.create(CodeEnum.SUCCESS);
        }
        List<Timing> timings = timingDAO.selectTimingByGroupId(groupDeviceMiddle.getGroupId(),
                projId, Timing.DEL_NO);
        for (Timing timing : timings) {
            if (!StringUtils.isEmpty(timing.getTaskName())) {
                TimingCron timingCron = timingCronDAO.selectAllByTaskName(timing.getTaskName());
                QuartzJobDto quartzJobDto =
                        JSON.parseObject(timingCron.getCronJson(), QuartzJobDto.class);
                String jsonParams = quartzJobDto.getJobDO().getExtInfo().getJsonParams();
                CommandSend commandSend = JSON.parseObject(jsonParams, CommandSend.class);
                List<CmdSendDto> varIdS = commandSend.getVarIdS();
                varIdS.add(new CmdSendDto(groupDeviceMiddle.getRegisId(),
                        Integer.valueOf(timing.getRunVarlue())));
                commandSend.setVarIdS(varIdS);
                quartzJobDto.getJobDO().getExtInfo().setJsonParams(JSON.toJSONString(commandSend));
                ReturnInfo<Boolean> returnInfo = taskService.updateTask(quartzJobDto);
                if (!returnInfo.getData()) {
                    LOG.error("添加群组变量到关联时序失败:", JSON.toJSONString(quartzJobDto));
                    throw new Exception("添加群组变量到关联时序失败.");
                }
                //更新
                String jsonString = JSON.toJSONString(quartzJobDto);
                timingCron.setCronJson(jsonString);
                timingCronDAO.updateByPrimaryKey(timingCron);
            }
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    /**
     * 修改群组设备
     * 
     * @param groupDeviceMiddle
     * @return
     */
    @Override
    public ReturnInfo updateGroupDevice(GroupDeviceMiddle groupDeviceMiddle) {
        groupDeviceMiddleDAO.updateGroupDevice(groupDeviceMiddle);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }
}
