package com.dikong.lightcontroller.vo;

import com.dikong.lightcontroller.dto.BasePage;

/**
 * <p>
 * Description 系统管理列表查询
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月25日下午5:51
 * @see </P>
 */
public class SystemSearch extends BasePage{

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
