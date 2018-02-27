package com.dikong.lightcontroller.utils;

import org.springframework.cglib.proxy.InvocationHandler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月26日下午5:46
 * @see </P>
 */
public class JedisHandler implements InvocationHandler{

    private JedisPool jedisPool;

    public JedisHandler(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object invoke = method.invoke(jedis, objects);
            return invoke;
        }finally {
            if (null != jedis){
                jedis.close();
            }
        }
    }
}
