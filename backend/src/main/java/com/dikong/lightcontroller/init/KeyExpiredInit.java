package com.dikong.lightcontroller.init;

import com.dikong.lightcontroller.listener.DeviceStatusListener;
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
    private DeviceStatusListener deviceStatusListener;

    @Override
    public void run(String... args) throws Exception {
        subscriber.subscriber();
        deviceStatusListener.deviceStatusSearch();
    }
}
