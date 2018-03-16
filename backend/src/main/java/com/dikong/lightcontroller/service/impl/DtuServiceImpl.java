package com.dikong.lightcontroller.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.dikong.lightcontroller.common.BussinessCode;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.DtuDAO;
import com.dikong.lightcontroller.dto.DeviceApi;
import com.dikong.lightcontroller.dto.DtuList;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.entity.Dtu;
import com.dikong.lightcontroller.service.DeviceService;
import com.dikong.lightcontroller.service.DtuService;
import com.dikong.lightcontroller.service.api.DtuCollectionApi;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.JedisProxy;
import com.dikong.lightcontroller.utils.Slf4jLogCollection;
import com.github.pagehelper.PageHelper;

import feign.Feign;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午2:25
 * @see
 *      </P>
 */
@Service
public class DtuServiceImpl implements DtuService {

    private static final Logger LOG = LoggerFactory.getLogger(DtuServiceImpl.class);

    private static final String DTU_ONLINE = "dtu.online.status";

    @Autowired
    private DtuDAO dtuDAO;

    @Autowired
    private JedisPool jedisPool;

    private DtuCollectionApi dtuCollectionApi;


    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private BlockingQueue deviceQueue;

    private DtuServiceImpl() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String collectionHost = properties.getProperty("collection.host");
        if (null == collectionHost) {
            throw new NullPointerException("collection.host is not null");
        }
        Slf4jLogCollection slf4jLogCollection = new Slf4jLogCollection();
        slf4jLogCollection.setEnable(true);
        this.dtuCollectionApi = Feign.builder().decoder(new JacksonDecoder())
                .logLevel(feign.Logger.Level.FULL).logger(slf4jLogCollection)
                .retryer(new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(1L), 0))
                .encoder(new JacksonEncoder()).target(DtuCollectionApi.class, collectionHost);
    }

    @Override
    public ReturnInfo<List<Dtu>> list(DtuList dtuList) {
        PageHelper.startPage(dtuList.getPageNo(), dtuList.getPageSize());
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<Dtu> dtus = dtuDAO.selectAllByPage(Dtu.DEL_NO, projId, dtuList.getDtuName());
        if (null == dtus) {
            dtus = new ArrayList<Dtu>();
        }
        return ReturnInfo.createReturnSucces(dtus);
    }

    @Override
    @Transactional
    public ReturnInfo deleteDtu(Long id) {
        Dtu dtu = dtuDAO.selectDtuById(id);
        dtuCollectionApi.deleteDevice(dtu.getDeviceCode());
        dtuDAO.updateIsDelete(id, Dtu.DEL_YES);
        deviceService.deleteDeviceByDtuId(id);

        int projId = AuthCurrentUser.getCurrentProjectId();
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        jedis.decr(String.valueOf(projId));
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo deleteAllDtu() {
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<Dtu> dtus = dtuDAO.selectAllDtuId(projId, Dtu.DEL_NO);
        if (!CollectionUtils.isEmpty(dtus)) {
            dtus.forEach(dtu -> deleteDtu(dtu.getId()));
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    @Transactional
    public ReturnInfo addDtu(Dtu dtu) {
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        int projId = AuthCurrentUser.getCurrentProjectId();
        try {
            Dtu existDtu = dtuDAO.selectExistDeviceCode(dtu.getDeviceCode());
            if (null != existDtu && existDtu.getIsDelete().equals(Dtu.DEL_NO)) {
                return ReturnInfo.create(BussinessCode.DTU_CODE_EXIST.getCode(),
                        BussinessCode.DTU_CODE_EXIST.getMsg());
            }
            dtu.setProjId(projId);
            Long dtuDevice = jedis.incr(String.valueOf(projId));
            dtu.setDevice("DTU" + dtuDevice);
            if (existDtu == null) {
                dtuDAO.insertDtu(dtu);
                // 发送dtu信息
                dtuCollectionApi.createDevice(new DeviceApi(dtu.getDeviceCode(),
                        dtu.getBeatContent(), dtu.getBeatTime()));
            } else {
                Example example = new Example(Dtu.class);
                dtu.setIsDelete(Dtu.DEL_NO);
                example.createCriteria().andEqualTo("deviceCode", dtu.getDeviceCode());
                dtuDAO.updateByExampleSelective(dtu, example);
                dtuCollectionApi.modifyDevice(new DeviceApi(dtu.getDeviceCode(),
                        dtu.getBeatContent(), dtu.getBeatTime()));
            }
        } catch (Exception e) {
            jedis.decr(String.valueOf(projId));
            throw e;
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo<List<Dtu>> idList() {
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<Dtu> dtus = dtuDAO.selectAllDtuId(projId, Dtu.DEL_NO);
        return ReturnInfo.createReturnSuccessOne(dtus);
    }

    @Override
    @Transactional
    public ReturnInfo updateDtu(Dtu dtu) {
        dtuDAO.updateByPrimaryKeySelective(dtu);
        // 发送dtu信息
        dtuCollectionApi.modifyDevice(
                new DeviceApi(dtu.getDeviceCode(), dtu.getBeatContent(), dtu.getBeatTime()));
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo conncationInfo(String deviceCode, Integer line) {
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        String online = jedis.hget(DTU_ONLINE, deviceCode);
        if (!StringUtils.isEmpty(online) && line.equals(Integer.parseInt(online))) {
            return ReturnInfo.create(CodeEnum.SUCCESS);
        }
        if (new Integer(0).equals(line)) {
            dtuDAO.updateOnlineStatusByCode(deviceCode, Dtu.OFFLINE);
            jedis.hset(DTU_ONLINE, deviceCode, String.valueOf(line));
        } else if (new Integer(2).equals(line)) {
            dtuDAO.updateOnlineStatusByCode(deviceCode, Dtu.ONLINE);
            Dtu dtu = dtuDAO.selectExistDeviceCode(deviceCode);
            List<Device> deviceList = deviceDAO.selectAllByDtuId(dtu.getId(), Device.DEL_NO);
            for (Device device : deviceList) {
                try {
                    deviceQueue.put(device.getId());
                } catch (InterruptedException e) {
                    LOG.error("放入查找设备中状态队列失败:",e);
                }
            }
            jedis.hset(DTU_ONLINE, deviceCode, String.valueOf(line));
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo<List<Dtu>> allDtu() {
        List<Dtu> dtus = dtuDAO.selectAllDtu(Dtu.DEL_NO);
        return ReturnInfo.createReturnSuccessOne(dtus);
    }
}
