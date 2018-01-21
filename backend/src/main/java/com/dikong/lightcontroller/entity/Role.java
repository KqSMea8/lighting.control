package com.dikong.lightcontroller.entity;


public class Role {

    private Integer id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 1->启用，2->禁用
     */
    private Integer roleStatus;
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
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public Integer getRoleStatus() {
        return roleStatus;
    }
    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
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