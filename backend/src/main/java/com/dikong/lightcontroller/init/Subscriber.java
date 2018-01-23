package com.dikong.lightcontroller.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dikong.lightcontroller.listener.KeyExpiredListener;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author huangwenjun
 * @version 2018年1月23日 下午9:08:53
 */
@Component
public class Subscriber {

    @Autowired
    private Environment environment;

    @Async
    public void subscriber() {
        JedisPool pool =
                new JedisPool(new JedisPoolConfig(), environment.getProperty("redis.hosts"),
                        Integer.valueOf(environment.getProperty("redis.port")));
        Jedis jedis = pool.getResource();
        jedis.psubscribe(new KeyExpiredListener(), "__keyevent@*__:expired");
    }
}
