package com.dikong.lightcontroller.service;

import java.util.List;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.EquipmentMonitor;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
public interface EquipmentMonitorService {
    public ReturnInfo oneMonitor(Integer monitorId);

    public ReturnInfo add(EquipmentMonitor equipmentMonitor);

    public ReturnInfo del(Integer monitorId);

    public ReturnInfo list(Integer type, Integer id);

    public ReturnInfo update(EquipmentMonitor equipmentMonitor);

    public ReturnInfo changeStatus(Integer monitorId, String value);

    public ReturnInfo refreshStatus(Integer type, Integer panelId);

    public ReturnInfo sourceList();

    public ReturnInfo sourceList(Integer dtuId);

    public ReturnInfo delByVarIds(List<Long> varIds);

    public ReturnInfo delByGroupId(Long groupId);

    public ReturnInfo delByTiming(Long varId);

    public ReturnInfo updateByVarId(Long varId, int value);

    public ReturnInfo updateByGroupId(Long groupId, int value);

    public ReturnInfo updateByTiming(Long varId, int value);
}
