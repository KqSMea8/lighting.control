package com.dikong.lightcontroller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

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
@Component
public class BeanConfig {

    @Autowired
    private Environment environment;

    @Bean
    public Jedis jedis() {
        return new Jedis(environment.getProperty("redis.hosts"),
                Integer.valueOf(environment.getProperty("redis.port")));
    }
}
