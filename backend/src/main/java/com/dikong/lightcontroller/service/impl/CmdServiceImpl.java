package com.dikong.lightcontroller.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.dao.CmdRecordDao;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.DtuDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dto.SendMsgReq;
import com.dikong.lightcontroller.entity.CmdRecord;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.entity.Dtu;
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

    @Autowired
    private DtuDAO dtuDao;


    @Override
    public String readOneSwitch(long varId) {
        int varNum = 1;
        String reqResult = reqUtil(varId, ReadWriteEnum.READ, varNum);
        List<String> results = CmdMsgUtils.analysisSwitchCmd(reqResult, varNum);
        if (results.size() >= 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public List<String> readMuchSwitch(long varId, int varNum) {
        String reqResult = reqUtil(varId, ReadWriteEnum.READ, varNum);
        List<String> results = CmdMsgUtils.analysisSwitchCmd(reqResult, varNum);
        return results;
    }

    /**
     *
     * @param varId 变量id
     * @param switchEnum
     * @return
     */
    @Override
    public boolean writeSwitch(long varId, SwitchEnum switchEnum) {
        // 根据 long varId,查询变量信息
        Register register = registerDao.selectRegisById(varId);
        // 根据devId查询串口设备编码
        Device device = deviceDAO.selectDeviceById(register.getDeviceId());
        // 查询DTU信息
        Dtu dtu = dtuDao.selectDtuById(device.getDtuId());
        // 查询一个变量当前值，默认为1
        String sendMsg = CmdMsgUtils.assembleSendCmd(device.getCode(), ReadWriteEnum.WRITE,
                register.getRegisType(), Integer.valueOf(register.getRegisAddr()), switchEnum);
        SendMsgReq sendMsgReq =
                new SendMsgReq(ReadWriteEnum.WRITE.getCode(), device.getCode(), sendMsg);
        String response = "";
        // 判断是否成功
        return true;
    }


    @Override
    public boolean writeSwitch(Map<Long, Integer> allRegis) {
        if (null == allRegis) {
            return false;
        }
        for (Map.Entry<Long, Integer> map : allRegis.entrySet()) {
            writeSwitch(map.getKey(), SwitchEnum.getByCode(map.getValue()));
        }
        return true;
    }

    @Override
    public String readOneAnalog(long varId) {
        String reqResult = reqUtil(varId, ReadWriteEnum.READ, 1);
        List<String> results = CmdMsgUtils.analysisAnalogCmd(reqResult);
        if (results.size() >= 0) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public List<String> readMuchAnalog(long varId, int varNum) {
        String reqResult = reqUtil(varId, ReadWriteEnum.READ, varNum);
        List<String> results = CmdMsgUtils.analysisAnalogCmd(reqResult);
        return results;
    }

    @Override
    public boolean writeAnalog(long varId, int value) {
        String reqResult = reqUtil(varId, ReadWriteEnum.WRITE, value);
        if (reqResult.equals("true")) {
            return true;
        }
        return false;
    }

    private String reqUtil(long varId, ReadWriteEnum readWriteEnum, int varNum) {
        // 根据 long varId,查询变量信息
        Register register = registerDao.selectRegisById(varId);
        // 根据devId查询串口设备编码
        Device device = deviceDAO.selectDeviceById(register.getDeviceId());
        // 查询DTU信息
        Dtu dtu = dtuDao.selectDtuById(device.getDtuId());
        // 查询一个变量当前值，默认为1
        return reqUtil(dtu.getDeviceCode(), device.getCode(), readWriteEnum,
                register.getRegisType(), register.getRegisAddr(), varNum);
    }

    private String reqUtil(String deviceCode, String devAddr, ReadWriteEnum readWriteEnum,
            String varType, String varAddr, int varNum) {
        String sendMsg = CmdMsgUtils.assembleSendCmd(devAddr, readWriteEnum, varType,
                Integer.valueOf(varAddr), varNum);
        SendMsgReq sendMsgReq = new SendMsgReq(readWriteEnum.getCode(), deviceCode, sendMsg);
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
