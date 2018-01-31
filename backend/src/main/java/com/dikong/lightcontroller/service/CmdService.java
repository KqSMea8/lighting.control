package com.dikong.lightcontroller.service;

import java.util.List;
import java.util.Map;

import com.dikong.lightcontroller.dto.CmdSendDto;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:28:24
 */
public interface CmdService {

    public String readOneSwitch(long varId);

    public List<String> readMuchSwitch(long varId, int varNum);

    public boolean writeSwitch(long varId, SwitchEnum switchEnum);

    public boolean writeSwitch(List<CmdSendDto> allRegis);

    public String readOneAnalog(long varId);

    public List<String> readMuchAnalog(long varId, int varNum);

    public boolean writeAnalog(long varId, int value);
}


