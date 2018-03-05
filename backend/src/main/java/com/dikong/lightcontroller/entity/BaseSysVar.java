package com.dikong.lightcontroller.entity;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午10:46
 * @see
 *      </P>
 */
public class BaseSysVar {

    public static final Long SEQUENCE_VAR_ID = new Long(1);

    // 变量类型,1 时序变量
    public static final Integer SEQUENCE = 1;
    public static final Integer GROUP = 2;

    public static final String CLOSE_SYS_VALUE = "0";
    public static final String OPEN_SYS_VALUE = "1";

    private Long id;

    private String varName;
    // 模拟量还是数子量
    private String varType;
    /**
     * 变量地址,群组变量时这个值就是群组id 时序变量时这个值为指定的默认值,
     */
    private Long varId;
    /**
     * 变量值,是开还是关
     */
    private String varValue;

    private Integer projId;
    /**
     * 系统变量类型,1->时序变量,2->群组变量
     */
    private Integer sysVarType;

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

    public Long getVarId() {
        return varId;
    }

    public void setVarId(Long varId) {
        this.varId = varId;
    }

    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public Integer getSysVarType() {
        return sysVarType;
    }

    public void setSysVarType(Integer sysVarType) {
        this.sysVarType = sysVarType;
    }
}
