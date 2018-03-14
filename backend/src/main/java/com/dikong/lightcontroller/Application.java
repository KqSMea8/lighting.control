package com.dikong.lightcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dikong.lightcontroller.utils.SpringContextUtil;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication applicationWeb = new SpringApplication(Application.class);
        ApplicationContext contextWeb = applicationWeb.run(args);
        SpringContextUtil.setApplicationContext(contextWeb);
    }
}
