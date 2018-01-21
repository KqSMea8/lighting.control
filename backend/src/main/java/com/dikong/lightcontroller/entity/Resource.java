package com.dikong.lightcontroller.entity;

/**
 * 资源权限表
 * @author huangwenjun
 * @date 2018年01月20日
 */
public class Resource {

    private Integer id;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 菜单id
     */
    private Integer menuId;
    /**
     * 资源信息
     */
    private String resourceInfo;
    /**
     * 1->启用，2->禁用
     */
    private Integer resourceStatus;
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
    public String getResourceName() {
        return resourceName;
    }
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    public Integer getMenuId() {
        return menuId;
    }
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
    public String getResourceInfo() {
        return resourceInfo;
    }
    public void setResourceInfo(String resourceInfo) {
        this.resourceInfo = resourceInfo;
    }
    public Integer getResourceStatus() {
        return resourceStatus;
    }
    public void setResourceStatus(Integer resourceStatus) {
        this.resourceStatus = resourceStatus;
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