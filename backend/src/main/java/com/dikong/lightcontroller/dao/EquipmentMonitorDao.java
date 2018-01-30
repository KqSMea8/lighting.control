package com.dikong.lightcontroller.dao;

import com.dikong.lightcontroller.entity.EquipmentMonitor;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
public interface EquipmentMonitorDao
        extends Mapper<EquipmentMonitor>, InsertListMapper<EquipmentMonitor> {

}
