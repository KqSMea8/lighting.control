package com.dikong.lightcontroller.dto;

public class SendMsgReq {
    private int cmdType;
    private String registerMsg;
    private String cmd;

    public SendMsgReq() {
        super();
    }

    public SendMsgReq(int type, String devCode, String cmd) {
        super();
        this.cmdType = type;
        this.registerMsg = devCode;
        this.cmd = cmd;
    }

    public int getType() {
        return cmdType;
    }

    public void setType(int type) {
        this.cmdType = type;
    }

    public String getDevCode() {
        return registerMsg;
    }

    public void setDevCode(String devCode) {
        this.registerMsg = devCode;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "SendMsgReq [cmdType=" + cmdType + ", registerMsg=" + registerMsg + ", cmd=" + cmd
                + "]";
    }
}
