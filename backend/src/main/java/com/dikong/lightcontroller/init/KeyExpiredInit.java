package com.dikong.lightcontroller.init;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import com.dikong.lightcontroller.listener.DeviceStatusConsumer;
import com.dikong.lightcontroller.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月12日上午8:01
 * @see
 *      </P>
 */
@Component
@Order(5)
public class KeyExpiredInit implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(KeyExpiredInit.class);

    @Autowired
    private Subscriber subscriber;


    @Autowired
    private BlockingQueue queue;

    @Autowired
    private DeviceService deviceService;

    @Override
    public void run(String... args) throws Exception {
        subscriber.subscriber();
        DeviceStatusConsumer consumer = new DeviceStatusConsumer(queue,deviceService);
        new Thread(consumer).start();
    }
}
