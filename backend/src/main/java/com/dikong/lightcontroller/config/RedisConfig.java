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
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数（100个足够用了，没必要设置太大）
        config.setMaxTotal(100);
        //最大空闲连接数
        config.setMaxIdle(10);
        //获取Jedis连接的最大等待时间（10秒）
        config.setMaxWaitMillis(10 * 1000);
        //在获取Jedis连接时，自动检验连接是否可用
        config.setTestOnBorrow(false);
        //在将连接放回池中前，自动检验连接是否有效
        config.setTestOnReturn(true);
        //自动测试池中的空闲连接是否都是可用连接
        config.setTestWhileIdle(true);
        //创建连接池
        JedisPool pool =
                new JedisPool(config, environment.getProperty("redis.hosts"),
                                     Integer.valueOf(environment.getProperty("redis.port")));
        return pool;
    }
}
