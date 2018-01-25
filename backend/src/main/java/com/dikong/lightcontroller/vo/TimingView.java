package com.dikong.lightcontroller.vo;

import java.util.List;

/**
 * <p>
 * Description 时序节点查看功能
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月25日下午6:23
 * @see </P>
 */
public class TimingView {

    //周一
    private List<TimingList> monday;
    //周二
    private List<TimingList> tuesday;
    //周三
    private List<TimingList> wednesday;
    //周四
    private List<TimingList> thursday;
    //周五
    private List<TimingList> friday;
    //周六
    private List<TimingList> staturday;
    //周天
    private List<TimingList> sunday;

    public List<TimingList> getMonday() {
        return monday;
    }

    public void setMonday(List<TimingList> monday) {
        this.monday = monday;
    }

    public List<TimingList> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<TimingList> tuesday) {
        this.tuesday = tuesday;
    }

    public List<TimingList> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<TimingList> wednesday) {
        this.wednesday = wednesday;
    }

    public List<TimingList> getThursday() {
        return thursday;
    }

    public void setThursday(List<TimingList> thursday) {
        this.thursday = thursday;
    }

    public List<TimingList> getFriday() {
        return friday;
    }

    public void setFriday(List<TimingList> friday) {
        this.friday = friday;
    }

    public List<TimingList> getStaturday() {
        return staturday;
    }

    public void setStaturday(List<TimingList> staturday) {
        this.staturday = staturday;
    }

    public List<TimingList> getSunday() {
        return sunday;
    }

    public void setSunday(List<TimingList> sunday) {
        this.sunday = sunday;
    }
}
