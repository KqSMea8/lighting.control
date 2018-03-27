package com.dikong.lightcontroller.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 时序表
 * 
 * @author lengrongfu
 * @date 2018年01月20日
 */
public class Timing {

    // 普通节点
    public static final Integer ORDINARY_NODE = 1;
    // 指定节点
    public static final Integer SPECIFIED_NODE = 2;
    // 指定假日
    public static final Integer DESIGNATED_HOLIDAYS_NODE = 3;

    // 普通时间
    public static final Integer ORDINDRY_TIME = 1;
    // 天亮时间
    public static final Integer DAWN_TIME = 2;
    // 天黑时间
    public static final Integer DARK_TIME = 3;

    // 群组类型
    public static final Integer GROUP_TYPE = 1;
    // 设备类型
    public static final Integer DEVICE_TYPE = 2;
    // 变量类型
    public static final Integer REGISTER_TYPE = 3;

    // 1->停止
    public static final Byte STOP_YES = 1;
    // 0->不停止
    public static final Byte STOP_NO = 0;

    // 删除
    public static final Byte DEL_YES = 2;
    // 未删除
    public static final Byte DEL_NO = 1;
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 节点类型，1->普通节点，2->指定日节点，3->指定假日
     */
    private Integer nodeType;
    /**
     * 执行时间类型，1->普通时间，2->天亮时间，3->天黑时间
     */
    private Integer nodeContentRunTimeType;
    /**
     * 执行时间
     */
    private String nodeContentRunTime;

    /**
     * 选定执行城市
     */
    private String nodeContentCity;
    /**
     *
     * 如果是群组就为空，如果是设备，就保存设备下的变量id
     */
    private Long runVar;
    /**
     * 执行类型，1->群组类型，2->设备类型
     */
    private Integer runType;
    /**
     * 执行类型的id,群组id,设备id
     */
    private Long runId;
    /**
     * 有效执行周期
     */
    private String validCycle;

    /**
     * 项目id
     */
    private Integer projId;

    /**
     * 是否在节假日停止 1->停止 0->不停止
     */
    private Byte stopWork;
    /**
     * 普通节点中的周几执行,多个之间用,分割
     */
    private String weekList;

    /**
     * 变量或者群组的设定值 选定变量执行值。开关量（BV）为0或1[开：１关：０]；模拟量(AV)可设定为相应的数值
     */
    private String runVarlue;


    /**
     * 是否删除
     */
    private Byte isDelete;

    /**
     * 指定节点中的月list
     */
    private String monthList;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 创建人
     */
    private Integer createBy;
    /**
     * 更新人
     */
    private Integer updateBy;

    public Long getId() {
        return id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
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

    public String getNodeContentRunTime() {
        return nodeContentRunTime;
    }

    public void setNodeContentRunTime(String nodeContentRunTime) {
        this.nodeContentRunTime = nodeContentRunTime;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public Byte getStopWork() {
        return stopWork;
    }

    public void setStopWork(Byte stopWork) {
        this.stopWork = stopWork;
    }

    public String getWeekList() {
        return weekList;
    }

    public void setWeekList(String weekList) {
        this.weekList = weekList;
    }

    public String getRunVarlue() {
        return runVarlue;
    }

    public void setRunVarlue(String runVarlue) {
        this.runVarlue = runVarlue;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getMonthList() {
        return monthList;
    }

    public void setMonthList(String monthList) {
        this.monthList = monthList;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
