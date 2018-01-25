package com.dikong.lightcontroller.dto;

import java.util.List;

/**
 * @author huangwenjun
 * @Datetime 2018年1月24日
 */
public class TypeAddMenuReq {
    private Integer typeId;
    private List<Integer> menuId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public List<Integer> getMenuId() {
        return menuId;
    }

    public void setMenuId(List<Integer> menuId) {
        this.menuId = menuId;
    }
}
