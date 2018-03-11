package com.dikong.lightcontroller.dto;

/**
 * @author huangwenjun
 * @version 2018年3月11日 下午10:51:05
 */
public class ChangePwdReq {
    private String oldPwd;
    private String newPwd;


    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    @Override
    public String toString() {
        return "ChangePwdReq [oldPwd=" + oldPwd + ", newPwd=" + newPwd + "]";
    }
}
