package com.dikong.lightcontroller.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.dao.CmdRecordDao;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.DtuDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dto.CmdRes;
import com.dikong.lightcontroller.dto.CmdSendDto;
import com.dikong.lightcontroller.dto.SendCmdRes;
import com.dikong.lightcontroller.entity.CmdRecord;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.entity.Dtu;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.OkhttpUtils;
import com.dikong.lightcontroller.utils.cmd.CmdMsgUtils;
import com.dikong.lightcontroller.utils.cmd.ReadWriteEnum;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:28:30
 */
@Service
public class CmdServiceImpl implements CmdService {

    private static final String serviceIpKey = "net.service.ip";

    private static final Logger LOG = LoggerFactory.getLogger(CmdServiceImpl.class);
    @Autowired
    private CmdRecordDao cmdRecordDao;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private RegisterDAO registerDao;

    @Autowired
    private DtuDAO dtuDao;

    @Autowired
    private Environment envioroment;

    @Override
    public CmdRes<String> readOneSwitch(long varId) {
        int varNum = 1;
        CmdRes<String> reqResult = reqUtil(varId, ReadWriteEnum.READ, varNum);
        if (!reqResult.isSuccess()) {
            return new CmdRes<String>(false, reqResult.getData());
        }
        List<String> results = CmdMsgUtils.analysisSwitchCmd(reqResult.getData(), varNum);
        if (results.size() > 0) {
            return new CmdRes<String>(true, results.get(0));
        }
        return new CmdRes<String>(false, results.get(0));
    }

    @Override
    public CmdRes<List<String>> readMuchSwitch(long varId, int varNum) {
        CmdRes<String> reqResult = reqUtil(varId, ReadWriteEnum.READ, varNum);
        List<String> results = new ArrayList<String>();
        if (!reqResult.isSuccess()) {
            results.add(reqResult.getData());
            return new CmdRes<List<String>>(false, results);
        }
        results = CmdMsgUtils.analysisSwitchCmd(reqResult.getData(), varNum);
        return new CmdRes<List<String>>(false, results);
    }

    /**
     *
     * @param varId 变量id
     * @param switchEnum
     * @return
     */
    @Override
    public CmdRes<String> writeSwitch(long varId, SwitchEnum switchEnum) {
        // 根据 long varId,查询变量信息
        Register register = registerDao.selectRegisById(varId);
        // 根据devId查询串口设备编码
        Device device = deviceDAO.selectDeviceById(register.getDeviceId());
        // 查询DTU信息
        Dtu dtu = dtuDao.selectDtuById(device.getDtuId());
        // 查询一个变量当前值，默认为1
        String sendMsg = CmdMsgUtils.assembleSendCmd(device.getCode(), ReadWriteEnum.WRITE,
                register.getRegisType(), Integer.valueOf(register.getRegisAddr()), switchEnum);
        CmdRecord cmdRecord = new CmdRecord();
        cmdRecord.setDeviceCode(dtu.getDeviceCode());
        cmdRecord.setDevCode(device.getCode());
        cmdRecord.setRegisAddr(register.getRegisAddr());
        cmdRecord.setCmdInfo(sendMsg);
        cmdRecord.setCreateBy(AuthCurrentUser.getUserId());

        Map<String, String> req = new HashMap<String, String>();
        req.put("cmdType", String.valueOf(ReadWriteEnum.WRITE.getCode()));
        req.put("registerMsg", dtu.getDeviceCode());
        req.put("cmd", sendMsg);
        LOG.info("发送信息：" + JSON.toJSONString(req));
        String response = "";
        try {
            response = OkhttpUtils
                    .postFrom(envioroment.getProperty(serviceIpKey) + "/device/command", req, null);
        } catch (IOException e) {
            String info = "发送命令异常" + e.toString();
            LOG.error(info);
            e.printStackTrace();
            cmdRecord.setResult(info);
            cmdRecordDao.insert(cmdRecord);
            return new CmdRes<String>(false, "发送命令异常");
        }
        LOG.info("命令发送响应：" + response);
        if (StringUtils.isEmpty(response)) {
            String info = "返回值为空";
            cmdRecord.setResult(info);
            cmdRecordDao.insert(cmdRecord);
            return new CmdRes<String>(false, info);
        }
        SendCmdRes sendCmdRes = JSON.parseObject(response, SendCmdRes.class);
        if (sendCmdRes.getCode() == -1) {
            cmdRecord.setResult(sendCmdRes.getData());
            cmdRecordDao.insert(cmdRecord);
            return new CmdRes<String>(false, sendCmdRes.getData());
        }
        // 判断是否成功
        if (sendMsg.equals(sendCmdRes.getData())) {
            return new CmdRes<String>(true, sendCmdRes.getData());
        }
        return new CmdRes<String>(false, sendCmdRes.getData());
    }


    @Override
    public boolean writeSwitch(List<CmdSendDto> allRegis) {
        if (null == allRegis) {
            return false;
        }
        for (CmdSendDto regis : allRegis) {
            writeSwitch(regis.getRegisId(), SwitchEnum.getByCode(regis.getSwitchValue()));
        }
        return true;
    }

    @Override
    public CmdRes<String> readOneAnalog(long varId) {
        CmdRes<String> reqResult = reqUtil(varId, ReadWriteEnum.READ, 1);
        if (!reqResult.isSuccess()) {
            return new CmdRes<String>(false, reqResult.getData());
        }
        List<String> results = CmdMsgUtils.analysisAnalogCmd(reqResult.getData());
        if (results.size() > 0) {
            return new CmdRes<String>(false, results.get(0));
        }
        return new CmdRes<String>(false, results.get(0));
    }

    @Override
    public CmdRes<List<String>> readMuchAnalog(long varId, int varNum) {
        CmdRes<String> reqResult = reqUtil(varId, ReadWriteEnum.READ, varNum);
        List<String> results = new ArrayList<String>();
        if (!reqResult.isSuccess()) {
            results.add(reqResult.getData());
            return new CmdRes<List<String>>(false, results);
        }
        results = CmdMsgUtils.analysisAnalogCmd(reqResult.getData());
        return new CmdRes<List<String>>(false, results);
    }

    @Override
    public CmdRes<String> writeAnalog(long varId, int value) {
        CmdRes<String> reqResult = reqUtil(varId, ReadWriteEnum.WRITE, value);
        if (!reqResult.isSuccess()) {
            return new CmdRes<String>(false, reqResult.getData());
        }
        if ("true".equals(reqResult.getData())) {
            return new CmdRes<String>(true, reqResult.getData());
        }
        return new CmdRes<String>(false, reqResult.getData());
    }

    private CmdRes<String> reqUtil(long varId, ReadWriteEnum readWriteEnum, int varNum) {
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

    private CmdRes<String> reqUtil(String deviceCode, String devAddr, ReadWriteEnum readWriteEnum,
            String varType, String varAddr, int varNum) {
        String sendMsg = CmdMsgUtils.assembleSendCmd(devAddr, readWriteEnum, varType,
                Integer.valueOf(varAddr), varNum);
        // TODO 命令执行记录
        CmdRecord cmdRecord = new CmdRecord();
        cmdRecord.setDeviceCode(deviceCode);
        cmdRecord.setDevCode(devAddr);
        cmdRecord.setRegisAddr(varAddr);
        cmdRecord.setCmdInfo(sendMsg);
        cmdRecord.setCreateBy(AuthCurrentUser.getUserId());
        String response = "";
        Map<String, String> req = new HashMap<String, String>();
        req.put("cmdType", String.valueOf(readWriteEnum.getCode()));
        req.put("registerMsg", deviceCode);
        req.put("cmd", sendMsg);
        LOG.info("发送信息：" + JSON.toJSONString(req));
        try {
            response = OkhttpUtils
                    .postFrom(envioroment.getProperty(serviceIpKey) + "/device/command", req, null);
        } catch (IOException e) {
            String info = "发送命令异常" + e.toString();
            LOG.error(info);
            e.printStackTrace();
            cmdRecord.setResult(info);
            cmdRecordDao.insert(cmdRecord);
            return new CmdRes<String>(false, "发送命令异常");
        }
        LOG.info("命令发送响应：" + response);
        if (StringUtils.isEmpty(response)) {
            String info = "返回值为空";
            cmdRecord.setResult(info);
            cmdRecordDao.insert(cmdRecord);
            return new CmdRes<String>(false, info);
        }
        SendCmdRes sendCmdRes = JSON.parseObject(response, SendCmdRes.class);
        cmdRecord.setResult(sendCmdRes.getData());
        cmdRecordDao.insert(cmdRecord);
        if (sendCmdRes.getCode() == -1) {
            return new CmdRes<String>(false, sendCmdRes.getData());
        }
        if (readWriteEnum == ReadWriteEnum.WRITE) {
            // 判断是否成功
            if (sendMsg.equals(sendCmdRes.getData())) {
                return new CmdRes<String>(true, "true");
            } else {
                return new CmdRes<String>(true, "false");
            }
        }
        return new CmdRes<String>(true, sendCmdRes.getData());
    }
}
