package com.dikong.lightcontroller.dto;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月30日下午3:58
 * @see </P>
 */
public class CmdSendDto {
    /**
     * 变量值
     */
    private Long regisId;
    /**
     * 开关量值
     */
    private Integer switchValue;

    public CmdSendDto(){

    }

    public CmdSendDto(Long regisId, Integer switchValue) {
        this.regisId = regisId;
        this.switchValue = switchValue;
    }

    public Long getRegisId() {
        return regisId;
    }

    public void setRegisId(Long regisId) {
        this.regisId = regisId;
    }

    public Integer getSwitchValue() {
        return switchValue;
    }

    public void setSwitchValue(Integer switchValue) {
        this.switchValue = switchValue;
    }
}
