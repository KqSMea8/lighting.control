package com.dikong.lightcontroller.controller;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import io.swagger.annotations.ApiImplicitParam;
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
@Api(value = "TaskCallbackController", description = "任务回调管理")
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

    // public static final String DEVICE_STATUS_KEY = "device.status.key";

    @PostMapping(path = "/command/send")
    public ReturnInfo commandSend(@RequestBody CommandSend commandSend) {
        LOG.info("时序控制任务回调,回调参数为{}", commandSend);
        return timingService.callBack(commandSend);
    }


    @GetMapping(path = "/device/status/{deviceId}")
    public ReturnInfo deviceStatus(@PathVariable("deviceId") Long deviceId)
            throws InterruptedException {
        if (null == deviceId || deviceId == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        LOG.info("设置状态查找回调,设备id为{}", deviceId);
        queue.put(deviceId);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @ApiOperation(value = "节假日回调任务")
    @GetMapping(path = "/holiday/task/{taskName}")
    public ReturnInfo holidayTask(@PathVariable("taskName") String taskName) {
        LOG.info("节假日回调任务,任务id:{},当前时间是{}", taskName, new Date());
        return timingService.holidayTask();
    }

    @ApiOperation(value = "图控任务回调")
    @ApiImplicitParam(required = true, dataType = "Integer", name = "editNodeId",
            paramType = "path")
    @DeleteMapping("/callback/{tree-node-id}")
    public void callBack(@PathVariable("tree-node-id") Integer editNodeId) {
        System.out.println("call back success! 图控节点：" + editNodeId);
        return;
    }
}
