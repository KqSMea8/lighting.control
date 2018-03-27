package com.dikong.lightcontroller.listener;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dikong.lightcontroller.service.DeviceService;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年03月08日上午9:21
 * @see
 *      </P>
 */
public class DeviceStatusConsumer implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceStatusConsumer.class);

    protected BlockingQueue queue = null;

    private DeviceService deviceService;

    public DeviceStatusConsumer(BlockingQueue queue, DeviceService deviceService) {
        this.queue = queue;
        this.deviceService = deviceService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Long deviceId = (Long) queue.take();
                LOG.info("阻塞队列中读取返回值为:{}", deviceId);
                deviceService.conncationInfo(deviceId,true);
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("获取设备状态错误", e);
            }
        }
    }
}
