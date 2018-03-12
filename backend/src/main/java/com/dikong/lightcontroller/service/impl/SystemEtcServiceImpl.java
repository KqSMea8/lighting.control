package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.dao.BackUriDao;
import com.dikong.lightcontroller.dao.ManagerTypeUriDao;
import com.dikong.lightcontroller.entity.BackUri;
import com.dikong.lightcontroller.entity.ManagerTypeUri;
import com.dikong.lightcontroller.service.SystemEtcService;
import com.dikong.lightcontroller.utils.JedisProxy;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author huangwenjun
 * @version 2018年3月12日 下午11:07:37
 */
@Service
public class SystemEtcServiceImpl implements SystemEtcService {

    @Autowired
    private BackUriDao backUriDao;

    @Autowired
    private ManagerTypeUriDao managerTypeUriDao;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String refreshAuthList() {
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        // 所有需要做鉴权的uri
        List<BackUri> backUris = backUriDao.selectAll();
        jedis.set(Constant.USER.AUTH_LIST, JSON.toJSONString(backUris));
        List<ManagerTypeUri> managerTypeUris = managerTypeUriDao.selectAll();// 管理类型和uri关系
        jedis.set(Constant.USER.TYPE_AUTH_REALT, JSON.toJSONString(managerTypeUris));
        return "success";
    }

}
