package com.dikong.lightcontroller.vo;

import java.util.Date;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月22日下午4:02
 * @see </P>
 */
public class SysVarList {
    //变量id
    private Long id;
    //变量名称
    private String varName;
    //变量类型
    private String varType;
    //变量地址
    private String varAddr;
    //变量值
    private String varValue;
    //更新时间
    private Date varTime;

    private Integer itemType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public String getVarAddr() {
        return varAddr;
    }

    public void setVarAddr(String varAddr) {
        this.varAddr = varAddr;
    }

    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }

    public Date getVarTime() {
        return varTime;
    }

    public void setVarTime(Date varTime) {
        this.varTime = varTime;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
}
