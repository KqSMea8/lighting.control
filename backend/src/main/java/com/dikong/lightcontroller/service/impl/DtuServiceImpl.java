package com.dikong.lightcontroller.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.DtuDAO;
import com.dikong.lightcontroller.dto.DeviceApi;
import com.dikong.lightcontroller.dto.DtuList;
import com.dikong.lightcontroller.entity.Dtu;
import com.dikong.lightcontroller.service.DtuService;
import com.dikong.lightcontroller.service.api.DtuCollectionApi;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.github.pagehelper.PageHelper;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

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

    @Autowired
    private DtuDAO dtuDAO;

    private DtuCollectionApi dtuCollectionApi;

    private DtuServiceImpl() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String collectionHost = properties.getProperty("collection.host");
        if (null == collectionHost) {
            throw new NullPointerException("collection.host is not null");
        }
        this.dtuCollectionApi = Feign.builder().decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder()).target(DtuCollectionApi.class, collectionHost);
    }

    @Override
    public ReturnInfo list(DtuList dtuList) {
        PageHelper.startPage(dtuList.getPageNo(), dtuList.getPageSize());
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<Dtu> dtus = dtuDAO.selectAllByPage(Dtu.DEL_NO, projId);
        if (null == dtus) {
            dtus = new ArrayList<Dtu>();
        }
        return ReturnInfo.createReturnSucces(dtus);
    }

    @Override
    public ReturnInfo deleteDtu(Long id) {
        Dtu dtu = dtuDAO.selectDtuById(id);
        dtuCollectionApi.deleteDevice(dtu.getDeviceCode());
        dtuDAO.updateIsDelete(id, Dtu.DEL_YES);

        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo addDtu(Dtu dtu) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        dtu.setProjId(projId);
        dtuDAO.insertDtu(dtu);
        // 发送dtu信息
        dtuCollectionApi.createDevice(
                new DeviceApi(dtu.getDeviceCode(), dtu.getBeatContent(), dtu.getBeatTime()));
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo idList() {
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<Dtu> dtus = dtuDAO.selectAllDtuId(projId, Dtu.DEL_NO);
        return ReturnInfo.createReturnSuccessOne(dtus);
    }

    @Override
    public ReturnInfo updateDtu(Dtu dtu) {
        dtuDAO.updateByPrimaryKeySelective(dtu);
        // 发送dtu信息
        dtuCollectionApi.modifyDevice(
                new DeviceApi(dtu.getDeviceCode(), dtu.getBeatContent(), dtu.getBeatTime()));
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo conncationInfo(String deviceCode, Integer line) {
        if (Dtu.OFFLINE.equals(line.byteValue())) {
            dtuDAO.updateOnlineStatusByCode(deviceCode, Dtu.OFFLINE);
        } else if (Dtu.ONLINE.equals(line.byteValue())) {
            dtuDAO.updateOnlineStatusByCode(deviceCode, Dtu.ONLINE);
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }
}
