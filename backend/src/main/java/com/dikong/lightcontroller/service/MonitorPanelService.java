package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.MonitorPanel;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
public interface MonitorPanelService {
    public ReturnInfo add(MonitorPanel monitorPanel);

    public ReturnInfo del(Integer panelId);

    public ReturnInfo list();

    public ReturnInfo update(MonitorPanel monitorPanel);
}
