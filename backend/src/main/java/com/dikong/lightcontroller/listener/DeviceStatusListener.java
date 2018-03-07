package com.dikong.lightcontroller.listener;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.controller.TaskCallbackController;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年03月07日下午8:29
 * @see
 *      </P>
 */
@Component
public class DeviceStatusListener {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceStatusListener.class);

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private DeviceService deviceService;

    @Async
    public void deviceStatusSearch() {
        while (true) {
            try {
                Jedis jedis = jedisPool.getResource();
                List<String> stringList = jedis.blpop(0, TaskCallbackController.DEVICE_STATUS_KEY);
                LOG.info("redis队列中读取返回值为:{}", JSON.toJSONString(stringList));
                if (!CollectionUtils.isEmpty(stringList) && stringList.size() == 2
                        && stringList.get(0).equals(TaskCallbackController.DEVICE_STATUS_KEY)) {
                    Long deviceId = Long.valueOf(stringList.get(1));
                    deviceService.conncationInfo(deviceId);
                }
            } catch (Exception e) {
                LOG.error("获取设备状态错误",e);
            }
        }
    }
}
