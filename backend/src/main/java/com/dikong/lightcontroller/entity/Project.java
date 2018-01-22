package com.dikong.lightcontroller.entity;

import javax.persistence.Id;

/**
 * 项目信息表
 * 
 * @author huangwenjun
 * @date 2018年01月20日
 */
public class Project {

    @Id
    private Integer projectId;
    /**
     * 项目名称
     */
    private String projectName;

    private Integer projectStatus;
    /**
     * 1->启用，2->禁用
     */
    private Integer isDelete;

    private Integer createBy;

    private Integer updateBy;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
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
