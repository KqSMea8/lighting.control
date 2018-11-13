package com.dikong.lightcontroller.entity;

import java.util.Date;

import javax.persistence.Id;

/**
 * @author huangwenjun
 * @version 2018年9月8日 下午4:55:21
 */
public class GraphControlCurve {
    @Id
    private Integer id;
    private Integer projectId;
    private Integer editNodeId;
    private Integer varId;
    private String curveName;
    private String deviceName;
    private String varName;
    private Integer ratio;
    private String unit;
    private Date createTime;
    private Integer createBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getEditNodeId() {
        return editNodeId;
    }

    public void setEditNodeId(Integer editNodeId) {
        this.editNodeId = editNodeId;
    }

    public Integer getVarId() {
        return varId;
    }

    public void setVarId(Integer varId) {
        this.varId = varId;
    }

    public String getCurveName() {
        return curveName;
    }

    public void setCurveName(String curveName) {
        this.curveName = curveName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "GraphControlCurve [id=" + id + ", projectId=" + projectId + ", editNodeId="
                + editNodeId + ", varId=" + varId + ", createTime=" + createTime + ", createBy="
                + createBy + "]";
    }
}
