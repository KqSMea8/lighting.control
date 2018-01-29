package com.dikong.lightcontroller.entity;

import javax.swing.plaf.PanelUI;
import java.util.Date;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午4:46
 * @see </P>
 */
public class History {

    public static final Integer REGISTER_TYPE = 1;
    public static final Integer GROUP_TYPE = 2;
    public static final Integer SEQUENCE_TYPE = 3;

    private Long id;

    private Long varId;

    /**
     * 变量类型,1->设备变量,2->群组变量,3->时序变量
     */
    private Integer varType;

    private String varValue;

    private Integer createBy;

    private Date createTime;

    public History(){

    }

    public History(Long varId, Integer varType, String varValue) {
        this.varId = varId;
        this.varType = varType;
        this.varValue = varValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
