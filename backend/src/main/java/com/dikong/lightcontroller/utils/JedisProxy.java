package com.dikong.lightcontroller.utils;

import org.springframework.cglib.proxy.Enhancer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月26日下午5:19
 * @see </P>
 */
public class JedisProxy {

    private JedisPool jedisPool;

    public JedisProxy(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Jedis createProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Jedis.class);
        JedisHandler jedisHandler = new JedisHandler(jedisPool);
        enhancer.setCallback(jedisHandler);
        Object o = enhancer.create();
        Jedis jedis = null;
        if (o instanceof Jedis){
            jedis = (Jedis) o;
        }
        return jedis;
    }
}
