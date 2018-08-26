package com.dikong.lightcontroller.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author huangwenjun
 * @version 2018年8月25日 下午2:54:00
 */
public class GraphControlEditNode {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private Integer projectId;
    private Integer nodeId;
    // 组件类型1->底图 2->文字 3->开关量 4->模拟量 5->区域控件 6->曲线图控件
    private Integer assemblyType;
    private String assemblyContent;
    private Integer targetId;

    private Date createTime;
    private Date updateTime;
    private Integer createBy;
    private Integer updateBy;

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

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getAssemblyType() {
        return assemblyType;
    }

    public void setAssemblyType(Integer assemblyType) {
        this.assemblyType = assemblyType;
    }

    public String getAssemblyContent() {
        return assemblyContent;
    }

    public void setAssemblyContent(String assemblyContent) {
        this.assemblyContent = assemblyContent;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
