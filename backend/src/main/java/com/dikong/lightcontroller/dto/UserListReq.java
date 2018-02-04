package com.dikong.lightcontroller.dto;

/**
 * @author huangwenjun
 * @version 2018年2月2日 下午9:12:23
 */
public class UserListReq {
    private int pageNo = 1;
    private int pageSize = 20;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
