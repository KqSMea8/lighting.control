package com.dikong.lightcontroller.entity;

/**
 * 用户角色关联表
 * @author huangwenjun
 * @date 2018年01月20日
 */
public class UserRole {

    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 角色id
     */
    private Integer roleId;

    private Integer createBy;

    private Integer updateBy;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getRoleId() {
        return roleId;
    }
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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