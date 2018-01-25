package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.MonitorPanelDao;
import com.dikong.lightcontroller.entity.MonitorPanel;
import com.dikong.lightcontroller.service.MonitorPanelService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;

import tk.mybatis.mapper.entity.Example;

/**
 * @author huangwenjun
 * @Datetime 2018年1月25日
 */
@Service
public class MonitorPanelServiceImpl implements MonitorPanelService {

    @Autowired
    private MonitorPanelDao panelDao;

    @Override
    public ReturnInfo add(MonitorPanel monitorPanel) {
        monitorPanel.setProjectId(AuthCurrentUser.getCurrentProjectId());
        monitorPanel.setCreateBy(AuthCurrentUser.getUserId());
        panelDao.insertSelective(monitorPanel);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo del(Integer panelId) {
        MonitorPanel monitorPanel = new MonitorPanel();
        monitorPanel.setPanelId(panelId);
        monitorPanel.setIsDelete(2);
        monitorPanel.setUpdateBy(AuthCurrentUser.getUserId());
        panelDao.updateByPrimaryKeySelective(monitorPanel);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo list() {
        Example example = new Example(MonitorPanel.class);
        example.createCriteria().andEqualTo("isDelete", 1);
        List<MonitorPanel> panels = panelDao.selectByExample(example);
        return ReturnInfo.createReturnSuccessOne(panels);
    }

    @Override
    public ReturnInfo update(MonitorPanel monitorPanel) {
        monitorPanel.setIsDelete(2);
        monitorPanel.setUpdateBy(AuthCurrentUser.getUserId());
        panelDao.updateByPrimaryKeySelective(monitorPanel);
        return ReturnInfo.createReturnSuccessOne(null);
    }

}
