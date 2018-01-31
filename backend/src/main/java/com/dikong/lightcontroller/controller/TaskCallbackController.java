package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.service.DeviceService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.vo.CommandSend;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午9:00
 * @see
 *      </P>
 */
@RestController
public class TaskCallbackController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskCallbackController.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private DeviceService deviceService;

    @PostMapping(path = "/command/send")
    public ReturnInfo commandSend(@RequestBody CommandSend commandSend) {
        return taskService.callBack(commandSend);
    }

    @GetMapping(path = "/device/status/{deviceId}")
    public ReturnInfo deviceStatus(@Param("deviceId")Long deviceId){
        if (null == deviceId || deviceId == 0){
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return deviceService.conncationInfo(deviceId);
    }

}
