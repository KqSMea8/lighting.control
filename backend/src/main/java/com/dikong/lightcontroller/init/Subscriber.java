package com.dikong.lightcontroller.init;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.listener.KeyExpiredListener;
import com.dikong.lightcontroller.utils.JedisProxy;
import com.github.pagehelper.util.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author huangwenjun
 * @version 2018年1月23日 下午9:08:53
 */
@Component
public class Subscriber {

    private static final Logger LOG = LoggerFactory.getLogger(Subscriber.class);
    @Autowired
    private Environment environment;

    @Autowired
    private JedisPool jedisPool;

    @Async
    public void subscriber() {
        while (true) {
            try {
                Thread.sleep(10000);
                Jedis jedis = new JedisProxy(jedisPool).createProxy();
                LOG.info("开始用户登陆时间过期监听");
                // TODO 获取所有在线用户信息，判断其token是否存在，如果不存在则移除
                LOG.info("更新在线用户状态");
                Map<String, String> onlineUsers = jedis.hgetAll(Constant.LOGIN.ONLINE_USERS_KEY);
                Set<String> userInfos = onlineUsers.keySet();
                String token = null;
                for (String userId : userInfos) {
                    token = onlineUsers.get(userId);
                    if (StringUtil.isEmpty(token) || !jedis.exists(token)) {
                        jedis.hdel(Constant.LOGIN.ONLINE_USERS_KEY, userId);
                    }
                }
                jedis.psubscribe(new KeyExpiredListener(), "__keyevent@*__:expired");
            } catch (Exception e) {
                LOG.error("redis key过期监听异常" + e.toString());
                e.printStackTrace();
            }
        }
    }
}
