package com.dikong.lightcontroller.dto;

import java.util.List;

import com.dikong.lightcontroller.entity.Menu;
import com.dikong.lightcontroller.entity.Resource;
import com.dikong.lightcontroller.entity.Role;
import com.dikong.lightcontroller.entity.User;

/**
 * @author huangwenjun
 * @version 2018年1月21日 下午11:21:51
 */
public class LoginRes {
    private String token;
    private User userInfo;
    private List<Role> roles;
    private List<Menu> menus;
    private List<Resource> resources;
    private Integer currentProjectId;
    private boolean isManager = false;// 是否为管理员标识

    public LoginRes() {
        super();
    }

    public LoginRes(String token, User userInfo, List<Menu> menus, List<Resource> resources) {
        super();
        this.token = token;
        this.userInfo = userInfo;
        this.menus = menus;
        this.resources = resources;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Integer getCurrentProjectId() {
        return currentProjectId;
    }

    public void setCurrentProjectId(Integer currentProjectId) {
        this.currentProjectId = currentProjectId;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    @Override
    public String toString() {
        return "LoginRes [token=" + token + ", userInfo=" + userInfo + ", roles=" + roles
                + ", menus=" + menus + ", currentProjectId=" + currentProjectId + "]";
    }
}
