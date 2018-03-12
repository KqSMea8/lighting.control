package com.dikong.lightcontroller.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.dao.BackUriDao;
import com.dikong.lightcontroller.dao.ManagerTypeUriDao;
import com.dikong.lightcontroller.entity.BackUri;
import com.dikong.lightcontroller.entity.ManagerTypeUri;
import com.dikong.lightcontroller.utils.JedisProxy;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author huangwenjun
 * @Date 2018年3月12日
 */
@Component
@Order(10)
public class AuthListInit implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(AuthListInit.class);

    @Autowired
    private BackUriDao backUriDao;

    @Autowired
    private ManagerTypeUriDao managerTypeUriDao;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void run(String... args) throws Exception {
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        // 所有需要做鉴权的uri
        List<BackUri> backUris = backUriDao.selectAll();
        jedis.set(Constant.USER.AUTH_LIST, JSON.toJSONString(backUris));
        List<ManagerTypeUri> managerTypeUris = managerTypeUriDao.selectAll();// 管理类型和uri关系
        jedis.set(Constant.USER.TYPE_AUTH_REALT, JSON.toJSONString(managerTypeUris));
    }
}
