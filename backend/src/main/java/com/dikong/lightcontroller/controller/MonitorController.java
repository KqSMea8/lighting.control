package com.dikong.lightcontroller.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.EquipmentMonitor;
import com.dikong.lightcontroller.service.EquipmentMonitorService;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
@RestController
@RequestMapping("/light/monitor")
public class MonitorController {

    private EquipmentMonitorService monitorService;

    @PostMapping("/add")
    public ReturnInfo add(@RequestBody EquipmentMonitor equipmentMonitor) {
        equipmentMonitor.setMonitorId(null);
        return monitorService.add(equipmentMonitor);
    }

    @RequestMapping("/del/{monitor-id}")
    public ReturnInfo del(@PathVariable("monitor-id") Integer monitorId) {
        if (monitorId == null) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return monitorService.del(monitorId);
    }

    @PostMapping("/list")
    public ReturnInfo list() {
        return monitorService.list();
    }

    @PostMapping("/update")
    public ReturnInfo update(@RequestBody EquipmentMonitor equipmentMonitor) {
        return monitorService.update(equipmentMonitor);
    }

    @RequestMapping("/change")
    public ReturnInfo chageStatus() {
        return null;
    }

}
