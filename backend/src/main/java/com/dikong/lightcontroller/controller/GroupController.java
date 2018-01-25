package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Group;
import com.dikong.lightcontroller.entity.GroupDeviceMiddle;
import com.dikong.lightcontroller.service.GroupService;
import com.dikong.lightcontroller.vo.GroupList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日下午3:24
 * @see </P>
 */
@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    /**
     * 群组列表接口
     * @param groupList
     * @return
     */
    @PostMapping(path = "/group/list")
    public ReturnInfo list(@RequestBody GroupList groupList){
        return groupService.list(groupList);
    }

    /**
     * 添加群组接口
     * @param group 群组信息
     * @return
     */
    @PostMapping(path = "/group/add")
    public ReturnInfo addGroup(@RequestBody Group group){
        return groupService.add(group);
    }

    /**
     * 删除群组和群组关联的设备
     * @param id　群组id
     * @return
     */
    @DeleteMapping(path = "/group/del/{id}")
    public ReturnInfo deleteGroup(@PathVariable("id")Long id){
        if (null == id || id == 0){
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return groupService.deleteGroup(id);
    }

    /**
     * 获取群组下的所有设备
     * @param id 群组id
     * @return
     */
    @GetMapping(path = "/group/device/{id}")
    public ReturnInfo deviceList(@PathVariable("id")Long id){
        if (null == id || id == 0){
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return groupService.deviceList(id);
    }

    /**
     * 删除群组下的多个设备
     * @param middId　中间表id
     * @return
     */
    @DeleteMapping(path = "/group/del/device/ids")
    public ReturnInfo deleteGroupDevice(@RequestBody List<Long> middId){
        return groupService.deleteGroupDevice(middId);
    }

    /**
     * 添加群组下关联的设备
     * @param groupDeviceMiddle
     * @return
     */
    @PostMapping(path = "/group/add/device")
    public ReturnInfo addGroupDevice(@RequestBody GroupDeviceMiddle groupDeviceMiddle){
        return groupService.addGroupDevice(groupDeviceMiddle);
    }
}
