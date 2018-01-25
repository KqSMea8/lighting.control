package com.dikong.lightcontroller.dto;

/**
 * @author huangwenjun
 * @version 2018年1月23日 下午11:07:30
 */
public class UserProjectReq {
    private Integer userId;
    private Integer projectId;
    private Integer managerType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getManagerType() {
        return managerType;
    }

    public void setManagerType(Integer managerType) {
        this.managerType = managerType;
    }

}
