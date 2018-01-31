package com.dikong.lightcontroller.dto;

/**
 * <p>
 * Description 提交数据到
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月31日下午3:38
 * @see </P>
 */
public class DeviceApi {

    private String registerMsg;

    private String heartMsg;

    private Integer heartInterval;

    public DeviceApi(){

    }
    
    public DeviceApi(String registerMsg, String heartMsg, Integer heartInterval) {
        this.registerMsg = registerMsg;
        this.heartMsg = heartMsg;
        this.heartInterval = heartInterval;
    }

    public String getRegisterMsg() {
        return registerMsg;
    }

    public void setRegisterMsg(String registerMsg) {
        this.registerMsg = registerMsg;
    }

    public String getHeartMsg() {
        return heartMsg;
    }

    public void setHeartMsg(String heartMsg) {
        this.heartMsg = heartMsg;
    }

    public Integer getHeartInterval() {
        return heartInterval;
    }

    public void setHeartInterval(Integer heartInterval) {
        this.heartInterval = heartInterval;
    }
}
