package com.dikong.lightcontroller.controller;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.EquipmentMonitor;
import com.dikong.lightcontroller.service.EquipmentMonitorService;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
@Api(value = "MonitorController", description = "监控管理")
@RestController
@RequestMapping("/light/monitor")
public class MonitorController {

    @Autowired
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

    @PostMapping("/list/{type}/{id}")
    public ReturnInfo list(@PathVariable("type") Integer type, @PathVariable("id") Integer id) {
        if (type == null || id == null) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return monitorService.list(type, id);
    }

    @PostMapping("/update")
    public ReturnInfo update(@RequestBody EquipmentMonitor equipmentMonitor) {
        return monitorService.update(equipmentMonitor);
    }


    @RequestMapping("/change/{monitor-id}/{value}")
    public ReturnInfo chageStatus(@PathVariable("monitor-id") Integer monitorId,
            @PathVariable("value") String value) {
        return monitorService.changeStatus(monitorId, value);
    }

    @RequestMapping("/refresh/{type}/{panel-id}")
    public ReturnInfo refreshStatus(@PathVariable("type") Integer type,
            @PathVariable("panel-id") Integer panelId) {
        if (type == null || panelId == null) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return monitorService.refreshStatus(type, panelId);
    }

    @RequestMapping("/source/list/{dtu-id}")
    public ReturnInfo sourceList(@PathVariable("dtu-id") Integer dtuId) {
        if (dtuId == null) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        if (dtuId == 0) {
            return monitorService.sourceList();
        } else {
            return monitorService.sourceList(dtuId);
        }
    }
}
