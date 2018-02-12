package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.vo.DeviceBoardList;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.DeviceService;
import com.dikong.lightcontroller.vo.DeviceAdd;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午1:49
 * @see
 *      </P>
 */
@Api(value = "DeviceController",description = "串口设备管理")
@RestController
@RequestMapping("/light")
public class DeviceController {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;

    /**
     * 删除设备
     * 
     * @param id
     * @return
     */
    @DeleteMapping(path = "/device/del/{id}")
    public ReturnInfo deleteDevice(@PathVariable("id") Long id) {
        if (null == id || id == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return deviceService.deleteDevice(id);
    }

    /**
     * 添加设备
     * 
     * @param deviceAdd
     * @return
     */
    @PostMapping(path = "/device/add")
    public ReturnInfo addDevice(@RequestBody DeviceAdd deviceAdd) {
        return deviceService.addDevice(deviceAdd);
    }

    /**
     * 上传点表文件
     * 重复上传的时候就先去判断数据库是否存在，如果存在就看之前的是否变化，如果有就更新，
     * 不存在就插入
     * @param uploadfile
     * @param id 设备id
     * @return
     */
    @PostMapping(path = "/device/point/table/upload/{id}")
    public ReturnInfo uploadPointTable(@RequestParam("file") MultipartFile uploadfile,
            @PathVariable("id") Long id) {
        if (uploadfile.isEmpty()) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return deviceService.uploadPointTableFile(uploadfile, id);
    }

    /***
     * 变量编辑功能处 获取dtu下的所有下拉设备
     * 
     * @param dtuId
     * @return
     */
    @GetMapping(path = "/device/list/{dtuId}")
    public ReturnInfo<List<Device>> dtuIdList(@PathVariable("dtuId") Long dtuId) {
        if (null == dtuId || dtuId == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return deviceService.idList(dtuId);
    }

    /**
     * 选择所有的设备
     * 一个设备可以被多个组选中
     * @return
     */
    @GetMapping(path = "/device/all")
    public ReturnInfo<List<DeviceBoardList>> selectAllDeviceNoSelect() {
        return deviceService.selectAllSelectDevice();
    }



}
