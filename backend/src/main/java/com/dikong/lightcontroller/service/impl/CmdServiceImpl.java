package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.dao.CmdRecordDao;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dto.SendMsgReq;
import com.dikong.lightcontroller.entity.CmdRecord;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.entity.Register;
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

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private RegisterDAO registerDao;


    @Override
    public String readOneSwitch(String deviceCode, long devId, long varId) {
        int varNum = 1;
        String reqResult = reqUtil(deviceCode, devId, varId, ReadWriteEnum.READ, varNum);
        List<String> results = CmdMsgUtils.analysisSwitchCmd(reqResult, varNum);
        if (results.size() >= 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public List<String> readMuchSwitch(String deviceCode, long devId, long varId, int varNum) {
        String reqResult = reqUtil(deviceCode, devId, varId, ReadWriteEnum.READ, varNum);
        List<String> results = CmdMsgUtils.analysisSwitchCmd(reqResult, varNum);
        return results;
    }

    @Override
    public boolean writeSwitch(String deviceCode, long devId, long varId, SwitchEnum switchEnum) {
        // 根据devId查询串口设备编码
        Device device = deviceDAO.selectDeviceById(devId);
        // 根据 long varId,查询变量信息
        Register register = registerDao.selectRegisById(varId);
        // 查询一个变量当前值，默认为1
        String sendMsg = CmdMsgUtils.assembleSendCmd(device.getCode(), ReadWriteEnum.WRITE,
                register.getRegisType(), Integer.valueOf(register.getRegisAddr()), switchEnum);
        String response = "";
        // 判断是否成功
        return true;
    }

    @Override
    public String readOneAnalog(String deviceCode, long devId, long varId) {
        String reqResult = reqUtil(deviceCode, devId, varId, ReadWriteEnum.READ, 1);
        List<String> results = CmdMsgUtils.analysisAnalogCmd(reqResult);
        if (results.size() >= 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public List<String> readMuchAnalog(String deviceCode, long devId, long varId, int varNum) {
        String reqResult = reqUtil(deviceCode, devId, varId, ReadWriteEnum.READ, varNum);
        List<String> results = CmdMsgUtils.analysisAnalogCmd(reqResult);
        return results;
    }

    @Override
    public boolean writeAnalog(String deviceCode, long devId, long varId, int value) {
        String reqResult = reqUtil(deviceCode, devId, varId, ReadWriteEnum.WRITE, value);
        if (reqResult.equals("true")) {
            return true;
        }
        return false;
    }

    private String reqUtil(String deviceCode, long devId, long varId, ReadWriteEnum readWriteEnum,
            int varNum) {
        // 根据devId查询串口设备编码
        Device device = deviceDAO.selectDeviceById(devId);
        // 根据 long varId,查询变量信息
        Register register = registerDao.selectRegisById(varId);
        // 查询一个变量当前值，默认为1
        return reqUtil(deviceCode, device.getCode(), readWriteEnum, register.getRegisType(),
                register.getRegisAddr(), varNum);
    }

    private String reqUtil(String deviceCode, String devAddr, ReadWriteEnum readWriteEnum,
            String varType, String varAddr, int varNum) {
        String sendMsg = CmdMsgUtils.assembleSendCmd(devAddr, readWriteEnum, varType,
                Integer.valueOf(varAddr), varNum);
        SendMsgReq sendMsgReq = new SendMsgReq(deviceCode, sendMsg);
        // TODO 命令执行记录
        CmdRecord cmdRecord = new CmdRecord();
        cmdRecord.setDeviceCode(deviceCode);
        cmdRecord.setDevCode(devAddr);
        cmdRecord.setRegisAddr(varAddr);
        cmdRecord.setCmdInfo(sendMsg);
        cmdRecord.setCreateBy(AuthCurrentUser.getUserId());
        cmdRecordDao.insert(cmdRecord);
        String response = "";
        if (readWriteEnum == ReadWriteEnum.WRITE) {
            // 判断是否成功
            return "true";
        }
        return response;
    }
}
