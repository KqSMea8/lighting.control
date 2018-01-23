package com.dikong.lightcontroller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dikong.lightcontroller.utils.SpringContextUtil;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.dikong.lightcontroller.dao"})
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication applicationWeb = new SpringApplication(Application.class);
        ApplicationContext contextWeb = applicationWeb.run(args);
        SpringContextUtil.setApplicationContext(contextWeb);
    }
}
