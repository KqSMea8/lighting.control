package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.dao.CmdRecordDao;
import com.dikong.lightcontroller.dto.SendMsgReq;
import com.dikong.lightcontroller.entity.CmdRecord;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.cmd.CmdMsgUtils;
import com.dikong.lightcontroller.utils.cmd.ReadWriteEnum;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:28:30
 */
@Service
public class CmdServiceImpl implements CmdService {

    @Autowired
    private CmdRecordDao cmdRecordDao;

    @Override
    public String readOneSwitch(String deviceCode, long devId, long varId) {
        int varNum = 1;
        String reqResult = reqUtil(deviceCode, devId,varId, ReadWriteEnum.READ, varNum);
        List<String> results = CmdMsgUtils.analysisSwitchCmd(reqResult, varNum);
        if (results.size() >= 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public List<String> readMuchSwitch(String deviceCode, long devId, long varId, int varNum) {
        String reqResult = reqUtil(deviceCode, devId,varId, ReadWriteEnum.READ, varNum);
        List<String> results = CmdMsgUtils.analysisSwitchCmd(reqResult, varNum);
        return results;
    }

    @Override
    public boolean writeSwitch(String deviceCode, long devId, long varId, SwitchEnum switchEnum) {
        // 根据dtuId、devId查询 在DTU中得顺序，根据顺序判断地址，最好来一个顺序，就是串口设备地址
        int equipmentOrder = 0;
        // 根据 long varId,查询变量信息
        String varType = "BI";
        int addressInfo = 1;// 起始地址
        // 查询一个变量当前值，默认为1
        String sendMsg = CmdMsgUtils.assembleSendCmd(equipmentOrder, ReadWriteEnum.WRITE, varType,
                addressInfo, switchEnum);

        return true;
    }

    @Override
    public String readOneAnalog(String deviceCode, long devId, long varId) {
        String reqResult = reqUtil(deviceCode,devId, varId, ReadWriteEnum.READ, 1);
        List<String> results = CmdMsgUtils.analysisAnalogCmd(reqResult);
        if (results.size() >= 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public List<String> readMuchAnalog(String deviceCode, long devId, long varId, int varNum) {
        String reqResult = reqUtil(deviceCode,devId, varId, ReadWriteEnum.READ, varNum);
        List<String> results = CmdMsgUtils.analysisAnalogCmd(reqResult);
        return results;
    }

    @Override
    public boolean writeAnalog(String deviceCode, long devId, long varId, int value) {
        String reqResult = reqUtil(deviceCode,devId, varId, ReadWriteEnum.READ, value);
        if (reqResult.equals("true")) {
            return true;
        }
        return false;
    }

    private String reqUtil(String deviceCode,long devId, long varId, ReadWriteEnum readWriteEnum, int varNum) {
        // 根据dtuId、devId查询 在DTU中得顺序，根据顺序判断地址，最好来一个顺序，就是串口设备地址
        int equipmentOrder = 0;
        // 根据 long varId,查询变量信息
        String varType = "BI";
        int addressInfo = 1;// 起始地址
        // 查询一个变量当前值，默认为1
        String sendMsg = CmdMsgUtils.assembleSendCmd(equipmentOrder, readWriteEnum, varType,
                addressInfo, varNum);
        SendMsgReq sendMsgReq = new SendMsgReq(deviceCode, sendMsg);
        //TODO 命令执行记录
        CmdRecord cmdRecord=new CmdRecord();
        cmdRecord.setDevId(devId);
        cmdRecord.setVarId(varId);
        cmdRecord.setCmdInfo(sendMsg);
        cmdRecord.setCreateBy(AuthCurrentUser.getUserId());
        String response = "";
        if (readWriteEnum == ReadWriteEnum.WRITE) {
            // 判断是否成功
            return "true";
        }
        return response;
    }
}
