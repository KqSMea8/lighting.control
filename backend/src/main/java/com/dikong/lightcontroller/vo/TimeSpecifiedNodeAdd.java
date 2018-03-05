package com.dikong.lightcontroller.vo;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月25日上午11:34
 * @see </P>
 */
public class TimeSpecifiedNodeAdd extends TimeOrdinaryNodeAdd{
    //月list
    @NotNull
    private String[] monthList;

    public String[] getMonthList() {
        return monthList;
    }

    public void setMonthList(String[] monthList) {
        this.monthList = monthList;
    }
}
