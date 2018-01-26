package com.dikong.lightcontroller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.MonitorPanel;
import com.dikong.lightcontroller.service.MonitorPanelService;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
@RestController
@RequestMapping("/light/panel")
public class PanelController {

    @Autowired
    private MonitorPanelService panelService;

    @PostMapping("/add")
    public ReturnInfo add(@RequestBody MonitorPanel monitorPanel) {
        monitorPanel.setPanelId(null);
        return panelService.add(monitorPanel);
    }

    @RequestMapping("/del/{panel-id}")
    public ReturnInfo del(@PathVariable("panel-id") Integer panelId) {
        if (panelId == null) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return panelService.del(panelId);
    }

    @PostMapping("/list")
    public ReturnInfo list() {
        return panelService.list();
    }

    @PostMapping("/update")
    public ReturnInfo update(@RequestBody MonitorPanel monitorPanel) {
        if (monitorPanel.getPanelId() == null) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return panelService.update(monitorPanel);
    }
}
