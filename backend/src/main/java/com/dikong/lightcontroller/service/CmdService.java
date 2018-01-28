package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:28:24
 */
public interface CmdService {
    public void readSwitch(String deviceCode, long devId, long varId, SwitchEnum switchEnum);

    public void writeSwitch();

    public void readAnalog();

    public void writeAnalog();
}
