package com.dikong.lightcontroller.dto;

/**
 * @author huangwenjun
 * @version 2018年1月21日 下午8:37:33
 */
public class LoginReqDto {
    private String username;
    private String password;
    private String verificationCode;
    private String codeToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getCodeToken() {
        return codeToken;
    }

    public void setCodeToken(String codeToken) {
        this.codeToken = codeToken;
    }
}
