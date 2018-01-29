package com.dikong.lightcontroller.dto;

public class SendMsgReq {
    private int type;
    private String devCode;
    private String cmd;

    public SendMsgReq() {
        super();
    }

    public SendMsgReq(int type, String devCode, String cmd) {
        super();
        this.type = type;
        this.devCode = devCode;
        this.cmd = cmd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
