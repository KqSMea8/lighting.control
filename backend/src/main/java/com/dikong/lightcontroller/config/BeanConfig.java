package com.dikong.lightcontroller.config;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月12日上午8:03
 * @see
 *      </P>
 */
@Configuration
public class BeanConfig {

    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.dikong.lightcontroller.dao");
        Properties properties = new Properties();
        properties.setProperty("mappers", "com.dikong.lightcontroller.dao.ManagerTypeMenuDao");
        properties.setProperty("mappers", "com.dikong.lightcontroller.dao.EquipmentMonitorDao");
        configurer.setProperties(properties);
        return configurer;
    }


    @Bean
    public BlockingQueue getQueue(){
        BlockingQueue queue = new ArrayBlockingQueue(1024);
        return queue;
    }


}
