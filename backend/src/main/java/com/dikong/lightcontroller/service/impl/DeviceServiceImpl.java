package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.DtuDAO;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.service.DeviceService;
import com.dikong.lightcontroller.vo.DeviceList;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午1:49
 * @see </P>
 */
@Service
public class DeviceServiceImpl implements DeviceService{

    private static final Logger LOG = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private DtuDAO dtuDAO;



    @Override
    public ReturnInfo list(Long dtuId) {
        List<Device> deviceList = deviceDAO.selectAllByDtuId(dtuId);
        List<DeviceList> deviceLists = new ArrayList<>();
        if (!CollectionUtils.isEmpty(deviceList)){
            String dtuName = dtuDAO.selectAllNameById(dtuId);
            deviceList.forEach(item->{
                DeviceList device = new DeviceList();
                BeanUtils.copyProperties(item,device);
                device.setDtuName(dtuName);
            });
        }
        return ReturnInfo.createReturnSuccessOne(deviceLists);
    }
}
