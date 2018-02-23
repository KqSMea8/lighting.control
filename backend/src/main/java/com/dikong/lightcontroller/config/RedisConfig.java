package com.dikong.lightcontroller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author huangwenjun
 * @Datetime 2018年1月24日
 */

@Configuration
public class RedisConfig {
    @Autowired
    private Environment environment;

//    @Bean
//    public Jedis jedis() {
//        JedisPool pool =
//                new JedisPool(new JedisPoolConfig(), environment.getProperty("redis.hosts"),
//                        Integer.valueOf(environment.getProperty("redis.port")));
//        return pool.getResource();
//    }

    @Bean
    public JedisPool getJedisPool(){
        JedisPool pool =
                new JedisPool(new JedisPoolConfig(), environment.getProperty("redis.hosts"),
                                     Integer.valueOf(environment.getProperty("redis.port")));
        return pool;
    }
}
