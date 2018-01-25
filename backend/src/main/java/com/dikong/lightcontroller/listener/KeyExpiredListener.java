package com.dikong.lightcontroller.listener;

import java.util.Map;

import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.utils.SpringContextUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author huangwenjun
 * @version 2018年1月23日 下午9:10:12
 */
public class KeyExpiredListener extends JedisPubSub {

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        Jedis jedis = (Jedis) SpringContextUtil.getBean(Jedis.class);
        Map<String, String> onlineUsers = jedis.hgetAll(Constant.LOGIN.ONLINE_USERS_KEY);
        for (String userId : onlineUsers.keySet()) {
            String token = onlineUsers.get(userId);
            if (token.equals(message)) {
                jedis.hdel(Constant.LOGIN.ONLINE_USERS_KEY, userId);
                return;
            }
        }
    }
}
