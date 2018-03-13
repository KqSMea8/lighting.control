package com.dikong.lightcontroller.service;

import java.util.List;

import com.dikong.lightcontroller.dto.CmdRes;
import com.dikong.lightcontroller.dto.CmdSendDto;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:28:24
 */
public interface CmdService {

    public CmdRes<String> readOneSwitch(long varId);

    public CmdRes<List<String>> readMuchSwitch(long varId, int varNum);

    public CmdRes<List<String>> readMuchVar(List<Register> varIds);

    public CmdRes<String> writeSwitch(long varId, SwitchEnum switchEnum);

    public int[] writeSwitch(List<CmdSendDto> allRegis);

    public CmdRes<String> readOneAnalog(long varId);

    public CmdRes<List<String>> readMuchAnalog(long varId, int varNum);

    public CmdRes<String> writeAnalog(long varId, int value);
}


