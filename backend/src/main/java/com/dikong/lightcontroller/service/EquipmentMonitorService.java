package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.EquipmentMonitor;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
public interface EquipmentMonitorService {
    public ReturnInfo add(EquipmentMonitor equipmentMonitor);

    public ReturnInfo del(Integer monitorId);

    public ReturnInfo list();

    public ReturnInfo update(EquipmentMonitor equipmentMonitor);
}
