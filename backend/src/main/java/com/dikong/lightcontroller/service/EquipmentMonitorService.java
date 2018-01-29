package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.EquipmentMonitor;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
public interface EquipmentMonitorService {
    public ReturnInfo add(EquipmentMonitor equipmentMonitor);

    public ReturnInfo del(Integer monitorId);

    public ReturnInfo list(Integer type, Integer id);

    public ReturnInfo update(EquipmentMonitor equipmentMonitor);

    public ReturnInfo chageStatus(Integer monitorId, String value);

    public ReturnInfo refreshStatus(Integer type, Integer panelId);

}
