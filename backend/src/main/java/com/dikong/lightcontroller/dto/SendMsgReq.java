package com.dikong.lightcontroller.dto;

public class SendMsgReq {
    private String devCode;
    private String cmd;
    
    public SendMsgReq() {
        super();
    }
    public SendMsgReq(String devCode, String cmd) {
        super();
        this.devCode = devCode;
        this.cmd = cmd;
    }
    public String getDevCode() {
        return devCode;
    }
    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }
    public String getCmd() {
        return cmd;
    }
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    @Override
    public String toString() {
        return "SendMsgReq [devCode=" + devCode + ", cmd=" + cmd + "]";
    }
}
