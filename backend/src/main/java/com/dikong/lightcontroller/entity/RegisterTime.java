package com.dikong.lightcontroller.entity;

import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月22日下午4:25
 * @see
 *      </P>
 */
public class RegisterTime extends Register {
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static int compareRegisAddr(RegisterTime r1, RegisterTime r2) {
        Long r1add = StringUtils.isEmpty(r1.getRegisAddr()) ? 0 : Long.parseLong(r1.getRegisAddr());
        Long r2add = StringUtils.isEmpty(r2.getRegisAddr()) ? 0 : Long.parseLong(r2.getRegisAddr());
        return r1add.compareTo(r2add);
    }
}
