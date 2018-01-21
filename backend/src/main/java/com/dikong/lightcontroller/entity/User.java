package com.dikong.lightcontroller.entity;

import javax.persistence.Id;

/**
 * 用户信息表
 * 
 * @author huangwenjun
 * @date 2018年01月20日
 */
public class User {

    @Id
    private Integer userId;
    /**
     * 用户名、登录名
     */
    private String userName;
    /**
     * 登陆密码
     */
    private String password;
    /**
     * 1->启用，2->禁用
     */
    private Integer userStatus;
    /**
     * 1->未删除 2->删除
     */
    private Integer isDelete;

    private Integer createBy;

    private Integer updateBy;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
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

    @Override
    public String toString() {
        return "User [id=" + userId + ", userName=" + userName + ", password=" + password
                + ", userStatus=" + userStatus + ", isDelete=" + isDelete + ", createBy=" + createBy
                + ", updateBy=" + updateBy + "]";
    }
}
