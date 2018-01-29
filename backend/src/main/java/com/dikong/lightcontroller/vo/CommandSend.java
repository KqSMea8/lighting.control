package com.dikong.lightcontroller.vo;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午9:01
 * @see </P>
 */
public class CommandSend {
    //变量id
    @NotNull
    private Long varId;
    //变量类型
    @NotNull
    private Integer varType;
    //变量值
    @NotNull
    private String varValue;

    public Long getVarId() {
        return varId;
    }

    public void setVarId(Long varId) {
        this.varId = varId;
    }

    public Integer getVarType() {
        return varType;
    }

    public void setVarType(Integer varType) {
        this.varType = varType;
    }

    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }
}
