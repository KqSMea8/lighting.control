package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.GroupDAO;
import com.dikong.lightcontroller.dao.GroupDeviceMiddleDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dto.DeviceDtu;
import com.dikong.lightcontroller.entity.Group;
import com.dikong.lightcontroller.entity.GroupDeviceMiddle;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.service.GroupService;
import com.dikong.lightcontroller.service.SysVarService;
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

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private GroupDeviceMiddleDAO groupDeviceMiddleDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private RegisterDAO registerDAO;


    @Autowired
    private SysVarService sysVarService;

    @Override
    public ReturnInfo list(GroupList groupList) {
        int projId = 0;
        groupList.setProjId(projId);
        groupList.setIsDelete(Group.DEL_NO);
        PageHelper.startPage(groupList.getPageNo(), groupList.getPageSize());
        List<Group> groups = groupDAO.selectAll(groupList);
        return ReturnInfo.createReturnSucces(groups);
    }

    @Override
    public ReturnInfo add(Group group) {
        int projId = 0;
        Integer lastGroupCode = groupDAO.selectLastCode(Group.DEL_NO, projId);
        if (null == lastGroupCode) {
            lastGroupCode = 0;
        }
        lastGroupCode += 1;
        group.setGroupCode(String.valueOf(lastGroupCode));
        group.setProjId(projId);
        groupDAO.addGroup(group);

        SysVar sysVar = new SysVar();
        sysVar.setSysVarType(SysVar.GROUP);
        sysVar.setVarValue(SysVar.DEFAULT_SYS_VALUE);
        sysVar.setVarName(group.getGroupName());
        sysVar.setVarId(group.getId());
        sysVar.setProjId(projId);
        sysVarService.addSysVar(sysVar);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo deleteGroup(Long id) {
        groupDAO.updateIsDelete(id, Group.DEL_YES);
        groupDeviceMiddleDAO.deleteByGroupId(id);

        sysVarService.deleteSysVar(id, SysVar.GROUP);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo deviceList(Long id) {
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
    public ReturnInfo addGroupDevice(GroupDeviceMiddle groupDeviceMiddle) {
        groupDeviceMiddleDAO.insert(groupDeviceMiddle);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }
}
