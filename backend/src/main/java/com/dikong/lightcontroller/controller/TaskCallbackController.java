package com.dikong.lightcontroller.controller;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import com.dikong.lightcontroller.service.AlarmSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.DeviceService;
import com.dikong.lightcontroller.service.SysVarService;
import com.dikong.lightcontroller.service.TimingService;
import com.dikong.lightcontroller.vo.CommandSend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
@Api(value = "TaskCallbackController",description = "任务回调管理")
@RestController
@RequestMapping("/light/callback")
public class TaskCallbackController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskCallbackController.class);

    @Autowired
    private SysVarService sysVarService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private TimingService timingService;
    
    @Autowired
    private BlockingQueue queue;

    @Autowired AlarmSettingService alarmSettingService;

//    public static final String DEVICE_STATUS_KEY = "device.status.key";

    @PostMapping(path = "/command/send")
    public ReturnInfo commandSend(@RequestBody CommandSend commandSend) {
        LOG.info("时序控制任务回调,回调参数为{}",commandSend);
        return timingService.callBack(commandSend);
    }


    @GetMapping(path = "/device/status/{deviceId}")
    public ReturnInfo deviceStatus(@PathVariable("deviceId") Long deviceId)
            throws InterruptedException {
        if (null == deviceId || deviceId == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        LOG.info("设置状态查找回调,设备id为{}",deviceId);
        queue.put(deviceId);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @ApiOperation(value = "节假日回调任务")
    @GetMapping(path = "/holiday/task/{taskName}")
    public ReturnInfo holidayTask(@PathVariable("taskName")String taskName){
        LOG.info("节假日回调任务,任务id:{},当前时间是{}",taskName,new Date());
        return timingService.holidayTask();
    }

    @ApiOperation(value = "项目告警回调任务")
    @GetMapping(path = "/alarm/{projectId}")
    public ReturnInfo alarm(@PathVariable("projectId")Integer projectId){
        if (projectId == 0){
            return ReturnInfo.create(true);
        }
        return alarmSettingService.triggerCallback(projectId);
    }
}
