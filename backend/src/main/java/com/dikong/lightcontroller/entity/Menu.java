package com.dikong.lightcontroller.entity;

/**
 * 菜单信息表
 * @author huangwenjun
 * @date 2018年01月20日
 */
public class Menu {

    private Integer id;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 1->启用，2->禁用
     */
    private Integer menuStatus;
    /**
     * 菜单地址
     */
    private String menuUrl;
    /**
     * 父菜单id
     */
    private Integer parentId;
    /**
     * 1->未删除 2->删除
     */
    private Integer isDelete;

    private Integer createBy;

    private Integer updateBy;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public Integer getMenuStatus() {
        return menuStatus;
    }
    public void setMenuStatus(Integer menuStatus) {
        this.menuStatus = menuStatus;
    }
    public String getMenuUrl() {
        return menuUrl;
    }
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public Integer getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    public Integer getCreateBy() {
        return createBy;
    }
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
    public Integer getUpdateBy() {
        return updateBy;
    }
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

}