package com.dikong.lightcontroller.service;

import java.util.List;

import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:28:24
 */
public interface CmdService {

    public String readOneSwitch(String deviceCode, long devId, long varId);


    public List<String> readMuchSwitch(String deviceCode, long devId, long varId, int varNum);

    public boolean writeSwitch(String deviceCode, long devId, long varId, SwitchEnum switchEnum);

    public String readOneAnalog(String deviceCode, long devId, long varId);

    public List<String> readMuchAnalog(String deviceCode, long devId, long varId, int varNum);

    public boolean writeAnalog(String deviceCode, long devId, long varId, int value);
}


