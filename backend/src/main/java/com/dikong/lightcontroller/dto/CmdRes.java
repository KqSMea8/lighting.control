package com.dikong.lightcontroller.dto;

/**
 * @author huangwenjun
 * @Datetime 2018年1月31日
 */
public class CmdRes<T> {
    private boolean success;
    private T data;
    private String projetId;
    private String deviceId;


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

    public String getProjetId() {
        return projetId;
    }

    public void setProjetId(String projetId) {
        this.projetId = projetId;
    }



    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "CmdRes{" + "success=" + success + ", data=" + data + '}';
    }
}
