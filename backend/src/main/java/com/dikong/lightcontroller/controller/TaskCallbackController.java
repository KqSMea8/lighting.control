package com.dikong.lightcontroller.controller;

import java.util.Date;

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
import com.dikong.lightcontroller.utils.JedisProxy;
import com.dikong.lightcontroller.vo.CommandSend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
    private JedisPool jedisPool;

    public static final String DEVICE_STATUS_KEY = "device.status.key";

    @PostMapping(path = "/command/send")
    public ReturnInfo commandSend(@RequestBody CommandSend commandSend) {
        LOG.info("时序控制任务回调,回调参数为{}",commandSend);
        return timingService.callBack(commandSend);
    }


    @GetMapping(path = "/device/status/{deviceId}")
    public ReturnInfo deviceStatus(@PathVariable("deviceId") Long deviceId) {
        if (null == deviceId || deviceId == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        LOG.info("设置状态查找回调,设备id为{}",deviceId);
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        jedis.rpush(DEVICE_STATUS_KEY,String.valueOf(deviceId));
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @ApiOperation(value = "节假日回调任务")
    @GetMapping(path = "/holiday/task/{taskName}")
    public ReturnInfo holidayTask(@PathVariable("taskName")String taskName){
        LOG.info("节假日回调任务,任务id:{},当前时间是{}",taskName,new Date());
        return timingService.holidayTask();
    }
}
