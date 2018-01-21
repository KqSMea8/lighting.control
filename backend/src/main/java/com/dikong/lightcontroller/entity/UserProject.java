package com.dikong.lightcontroller.entity;

/**
 * 用户项目关联表
 * @author huangwenjun
 * @date 2018年01月20日
 */
public class UserProject {

    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 项目id
     */
    private Integer projectId;

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
    public Integer getProjectId() {
        return projectId;
    }
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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