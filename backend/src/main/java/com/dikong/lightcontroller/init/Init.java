package com.dikong.lightcontroller.init;

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
@Order(1)
public class Init implements CommandLineRunner {



    @Override
    public void run(String... args) throws Exception {
        // tcpServer.run();
    }
}
