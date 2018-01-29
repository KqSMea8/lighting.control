package com.dikong.lightcontroller.utils.cmd;

/**
 * @author huangwenjun
 * @Datetime 2018年1月26日
 */
public enum ReadWriteEnum {
    READ(1), WRITE(0);

    private int code;

    /**
     * @param code
     * @Datetime 2018年1月26日
     */
    private ReadWriteEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
