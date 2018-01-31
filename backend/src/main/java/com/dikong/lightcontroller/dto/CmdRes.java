package com.dikong.lightcontroller.dto;

/**
 * @author huangwenjun
 * @Datetime 2018年1月31日
 */
public class CmdRes<T> {
    private boolean success;
    private T data;


    /**
     * 
     * @Datetime 2018年1月31日
     */
    public CmdRes() {
        super();
    }

    /**
     * @param success
     * @param data
     * @Datetime 2018年1月31日
     */
    public CmdRes(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
