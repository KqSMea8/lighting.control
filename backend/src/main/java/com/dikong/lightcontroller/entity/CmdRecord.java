package com.dikong.lightcontroller.entity;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:23:30
 */
public class CmdRecord {

    private Long recordId;
    private Long dtuId;
    private Long devId;
    private Long varId;
    private String cmdInfo;
    private int createBy;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getDtuId() {
        return dtuId;
    }

    public void setDtuId(Long dtuId) {
        this.dtuId = dtuId;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public Long getVarId() {
        return varId;
    }

    public void setVarId(Long varId) {
        this.varId = varId;
    }

    public String getCmdInfo() {
        return cmdInfo;
    }

    public void setCmdInfo(String cmdInfo) {
        this.cmdInfo = cmdInfo;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }
}
