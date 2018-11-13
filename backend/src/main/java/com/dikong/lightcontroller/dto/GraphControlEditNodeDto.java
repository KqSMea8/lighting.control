package com.dikong.lightcontroller.dto;

import java.math.BigDecimal;

import com.dikong.lightcontroller.entity.GraphControlEditNode;

/**
 * @author huangwenjun
 * @version 2018年8月30日 下午11:00:47
 */
public class GraphControlEditNodeDto extends GraphControlEditNode {
    private BigDecimal currentValue;
    // 模拟量还是数子量
    private String varType;
    private String varValue;

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }
}
