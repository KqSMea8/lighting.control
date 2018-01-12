package com.dikong.lightcontroller.init;

import com.dikong.lightcontroller.server.TcpServer;
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
@Order(1)
public class Init implements CommandLineRunner {

    @Autowired
    private TcpServer tcpServer;

    @Override
    public void run(String... args) throws Exception {
        tcpServer.run();
    }
}
