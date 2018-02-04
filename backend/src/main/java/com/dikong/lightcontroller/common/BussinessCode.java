package com.dikong.lightcontroller.common;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月21日下午10:25
 * @see
 *      </P>
 */
public enum BussinessCode {
    DEVICE_EXIST(-100, "设备Id已存在"),
    DTU_CODE_EXIST(-101,"DTU注册码已重复");
    private int code;

    private String msg;


    BussinessCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
