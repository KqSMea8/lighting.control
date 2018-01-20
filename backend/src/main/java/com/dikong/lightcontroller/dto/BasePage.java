package com.dikong.lightcontroller.dto;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午2:47
 * @see </P>
 */
public class BasePage {

    private static final Integer DEFAULT_PAGESIZE = 20;

    private int pageNo;

    private int pageSize = DEFAULT_PAGESIZE;

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
