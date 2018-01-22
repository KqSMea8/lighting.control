package com.dikong.lightcontroller.common;

import java.util.List;

import com.github.pagehelper.Page;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午2:30
 * @see
 *      </P>
 */
public class ReturnInfo {
    private Integer code;
    private String msg;
    private Object data;
    private PageNation pageNation;

    public ReturnInfo() {

    }


    public ReturnInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ReturnInfo create(int code, String msg, Object data, PageNation paginNation) {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.code = code;
        returnInfo.msg = msg;
        returnInfo.data = data;
        returnInfo.pageNation = paginNation;
        return returnInfo;
    }

    public static ReturnInfo create(CodeEnum codeEnum, Object data) {
        return create(codeEnum.getCode(), codeEnum.getMsg(), data, null);
    }

    public static ReturnInfo create(CodeEnum codeEnum) {
        return create(codeEnum.getCode(), codeEnum.getMsg());
    }

    public static ReturnInfo create(int code, String msg) {
        return create(code, msg, "", new PageNation());
    }

    public static ReturnInfo createReturnSucces(List<?> data) {
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(CodeEnum.SUCCESS.getCode());
        returnInfo.setMsg(CodeEnum.SUCCESS.getMsg());
        returnInfo.setData(data);
        PageNation paginnation = create(data);
        returnInfo.setPageNation(paginnation);
        return returnInfo;
    }

    public static ReturnInfo createReturnSuccessOne(Object data) {
        ReturnInfo returnInfo = create(CodeEnum.SUCCESS);
        returnInfo.setData(data);
        return returnInfo;
    }

    public static PageNation create(List<?> result) {
        PageNation paginnation = new PageNation();
        paginnation.setTotal(new Long(((Page<?>) result).getTotal()).intValue());
        paginnation.setPageNo(((Page<?>) result).getPageNum());
        paginnation.setPages(((Page<?>) result).getPages());
        paginnation.setPageSize(((Page<?>) result).getPageSize());
        return paginnation;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public PageNation getPageNation() {
        return pageNation;
    }

    public void setPageNation(PageNation pageNation) {
        this.pageNation = pageNation;
    }
}
