package com.dikong.lightcontroller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.dikong.lightcontroller.dao"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
