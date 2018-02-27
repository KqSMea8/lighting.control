package com.dikong.lightcontroller.vo;

import com.dikong.lightcontroller.dto.BasePage;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日上午10:17
 * @see
 *      </P>
 */
public class RegisterList extends BasePage{
    //设备id
    private Long deviceId;
    //变量类型
    private String regisType;

    public RegisterList(){

    }
    public RegisterList(Long deviceId){
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getRegisType() {
        return regisType;
    }

    public void setRegisType(String regisType) {
        this.regisType = regisType;
    }
}
