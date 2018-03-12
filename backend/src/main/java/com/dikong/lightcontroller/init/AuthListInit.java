package com.dikong.lightcontroller.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dikong.lightcontroller.utils.JedisProxy;

/**
 * @author huangwenjun
 * @Date 2018年3月12日
 */
public class AuthListInit implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(AuthListInit.class);

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void run(String... args) throws Exception {
        Jedis jedis = new JedisProxy(jedisPool).createProxy();

    }
}
