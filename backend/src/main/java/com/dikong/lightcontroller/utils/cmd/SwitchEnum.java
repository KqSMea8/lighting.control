package com.dikong.lightcontroller.utils.cmd;

/**
 * @author huangwenjun
 * @Datetime 2018年1月26日
 */
public enum SwitchEnum {
        OPEN(1, "ff00"), CLOSE(0, "0000");

    private int code;
    private String value;

    /**
     * @param code
     * @Datetime 2018年1月26日
     */
    private SwitchEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    /**
     * 
     * @Datetime 2018年1月29日
     */
    public static SwitchEnum getByCode(int code) {
        for (SwitchEnum temp : SwitchEnum.values()) {
            if (temp.getCode() == code) {
                return temp;
            }
        }
        return null;
    }
}
