package com.dikong.lightcontroller.service;

import java.util.List;
import java.util.Map;

import com.dikong.lightcontroller.dto.CmdRes;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:28:24
 */
public interface CmdService {

    public CmdRes<String> readOneSwitch(long varId);

    public CmdRes<List<String>> readMuchSwitch(long varId, int varNum);

    public CmdRes<String> writeSwitch(long varId, SwitchEnum switchEnum);

    public boolean writeSwitch(Map<Long, Integer> allRegis);

    public CmdRes<String> readOneAnalog(long varId);

    public CmdRes<List<String>> readMuchAnalog(long varId, int varNum);

    public CmdRes<String> writeAnalog(long varId, int value);
}


