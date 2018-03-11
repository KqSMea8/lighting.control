package com.dikong.lightcontroller.dto;

import java.util.List;

import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.entity.Menu;

/**
 * @author huangwenjun
 * @version 2018年3月11日 下午11:35:10
 */
public class EnterProjectRes {
    private List<Menu> menus;
    private int managerType;// 0->超级管理员 1->设备可控 2->可配置 3->设备可控、可配置



    public EnterProjectRes() {
        super();
        this.managerType = Constant.USER.PROJECT_MANAGER_CONTROL;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public int getManagerType() {
        return managerType;
    }

    public void setManagerType(int managerType) {
        this.managerType = managerType;
    }
}
