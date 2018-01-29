package com.dikong.lightcontroller.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

public class EquipmentMonitor {

    @Id
    private Integer monitorId;
    /**
     * 监控类型：1->设备监控 2->自定义监控
     */
    private Integer monitorType;
    /**
     * 显示文字信息
     */
    private String caption;
    /**
     * 监控对象(串口设备/群组/时序)id
     */
    private Integer sourceId;
    /**
     * 监控对象类型 1->单个设备 2->群组 3->时序
     */
    private Integer sourceType;

    /**
     * 当前值
     */
    private BigDecimal currentValue;

    /**
     * 当前值类型
     */
    private String valueType;
    /**
     * 单位
     */
    private String unit;
    /**
     * 进制换算
     */
    private BigDecimal factor;
    /**
     * 变量数值的最大值
     */
    private BigDecimal max;
    /**
     * 最小值
     */
    private BigDecimal min;

    /**
     * 面板id(设备监控为DTUid,自定义监控为自定义面板id)
     */
    private Integer panelId;
    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 1->未删除 2->删除
     */
    private Integer isDelete;

    private Integer createBy;

    private Integer updateBy;


    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    public Integer getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(Integer monitorType) {
        this.monitorType = monitorType;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
