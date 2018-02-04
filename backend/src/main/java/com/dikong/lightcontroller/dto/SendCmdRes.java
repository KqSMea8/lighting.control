package com.dikong.lightcontroller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author huangwenjun
 * @Datetime 2018年1月31日
 */
public class SendCmdRes {


    @JsonProperty(value = "Code")
    private Integer code;

    @JsonProperty("Data")
    private String data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SendCmdRes{" + "Code=" + code + ", Data='" + data + '\'' + '}';
    }
}
