package com.dikong.lightcontroller.dto;

import com.dikong.lightcontroller.entity.User;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午9:39:56
 */
public class AuthCurrentUserDto {
    private User userInfo;
    private int currentProjectId;

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public int getCurrentProjectId() {
        return currentProjectId;
    }

    public void setCurrentProjectId(int currentProjectId) {
        this.currentProjectId = currentProjectId;
    }
}
