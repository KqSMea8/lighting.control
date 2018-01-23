package com.dikong.lightcontroller.entity;

/**
 * 时序表
 * 
 * @author lengrongfu
 * @date 2018年01月20日
 */
public class Timing {

    private Long id;
    /**
     * 节点类型，1->普通节点，2->指定日节点，3->指定假日
     */
    private Integer nodeType;
    /**
     * 执行时间类型，1->普通时间，2->天亮时间，3->天黑时间
     */
    private Integer nodeContentRunTimeType;

    private String nodeContentCity;
    /**
     * 执行变量,可以是组id,设备id,变量id
     */
    private Long runVar;
    /**
     * 执行类型，1->群组类型，2->设备类型，3->变量类型
     */
    private Integer runType;
    /**
     * 有效执行周期
     */
    private String validCycle;

    /**
     * 项目id
     */
    private Integer projId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getNodeContentRunTimeType() {
        return nodeContentRunTimeType;
    }

    public void setNodeContentRunTimeType(Integer nodeContentRunTimeType) {
        this.nodeContentRunTimeType = nodeContentRunTimeType;
    }

    public String getNodeContentCity() {
        return nodeContentCity;
    }

    public void setNodeContentCity(String nodeContentCity) {
        this.nodeContentCity = nodeContentCity;
    }

    public Long getRunVar() {
        return runVar;
    }

    public void setRunVar(Long runVar) {
        this.runVar = runVar;
    }

    public Integer getRunType() {
        return runType;
    }

    public void setRunType(Integer runType) {
        this.runType = runType;
    }

    public String getValidCycle() {
        return validCycle;
    }

    public void setValidCycle(String validCycle) {
        this.validCycle = validCycle;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }
}
