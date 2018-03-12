package com.dikong.lightcontroller.vo;

import com.dikong.lightcontroller.dto.BasePage;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月22日下午4:05
 * @see </P>
 */
public class VarListSearch extends BasePage{

    private long id;

    //变量类型
    private String regisType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegisType() {
        return regisType;
    }

    public void setRegisType(String regisType) {
        this.regisType = regisType;
    }
}
