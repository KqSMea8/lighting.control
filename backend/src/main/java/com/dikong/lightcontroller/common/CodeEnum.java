package com.dikong.lightcontroller.common;

/**
 * 返回前端公用code码 Created by lrf on 17-7-28.
 */

public enum CodeEnum {
    NOT_CONTENT(1, "返回内容为空"),
    SUCCESS(0, "成功"),
    SERVER_ERROR(-1, "服务器错误"),
    ACCESS_REFULE(-2, "访问被拒绝"),
    REQUEST_METHOD_ERROR(-3, "请求方法错误"),
    REQUEST_PARAM_ERROR(-4, "请求参数错误"),
    REQUEST_JSON_FORMAT_ERR(-5, "请求json格式错误"),
    NOCOMPETENCE(-8, "无权限访问"),
    LOGIN_FAIL(-9, "账号或密码错误"),
    NO_LOGIN(-10, "用户过期或处在无登录状态"),
    SIGN_ERROR(-11, "验证签名错误"),
    DATA_INVALID(-12, "存在不合法数据"),
    SAME_LOAN(-13, "存在正在流程中的相同身份证号码的贷款"),
    NOTHING_DELETE(-14, "删除暂存信息失败,未找到匹配内容"),
    USER_EXIST(-15, "用户已存在"),
    OLD_PWD_ERROR(-16, "旧密码错误"),
    PWD_FORMAT_ERROR(-17, "密码长度最少6位"),
    FILE_EXIST(-18, "文件已存在"),
    MAC_FORBID_LOGIN(-19, "当前机器禁止登陆"),
    INPUT_SMS_CODE(-20, "请输入短信验证码"),
    SMS_CODE_ERROR(-21, "短信验证码错误");

    private int code;

    private String msg;


    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
