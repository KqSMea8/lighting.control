package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.EquipmentMonitor;
import com.dikong.lightcontroller.dao.EquipmentMonitorDao;
import com.dikong.lightcontroller.service.EquipmentMonitorService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;

import tk.mybatis.mapper.entity.Example;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
@Service
public class EquipmentMonitorServiceImpl implements EquipmentMonitorService {

    @Autowired
    private EquipmentMonitorDao monitorDao;

    @Override
    public ReturnInfo add(EquipmentMonitor equipmentMonitor) {
        equipmentMonitor.setPanelId(AuthCurrentUser.getCurrentProjectId());
        equipmentMonitor.setCreateBy(AuthCurrentUser.getUserId());
        monitorDao.insertSelective(equipmentMonitor);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo del(Integer monitorId) {
        EquipmentMonitor equipmentMonitor = new EquipmentMonitor();
        equipmentMonitor.setMonitorId(monitorId);
        equipmentMonitor.setUpdateBy(AuthCurrentUser.getUserId());
        equipmentMonitor.setIsDelete(2);
        monitorDao.updateByPrimaryKeySelective(equipmentMonitor);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo list() {
        // TODO
        Example example = new Example(EquipmentMonitor.class);
        example.createCriteria().andEqualTo("isDelete", 1);
        List<EquipmentMonitor> monitors = monitorDao.selectByExample(example);
        return ReturnInfo.createReturnSuccessOne(monitors);
    }

    @Override
    public ReturnInfo update(EquipmentMonitor equipmentMonitor) {
        equipmentMonitor.setUpdateBy(AuthCurrentUser.getUserId());
        monitorDao.updateByPrimaryKeySelective(equipmentMonitor);
        return ReturnInfo.createReturnSuccessOne(null);
    }

}
