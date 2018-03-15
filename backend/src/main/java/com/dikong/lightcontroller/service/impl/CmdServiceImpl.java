package com.dikong.lightcontroller.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.Constant;
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
import com.dikong.lightcontroller.utils.JedisProxy;
import com.dikong.lightcontroller.utils.OkhttpUtils;
import com.dikong.lightcontroller.utils.RedisLockUtils;
import com.dikong.lightcontroller.utils.cmd.CmdMsgUtils;
import com.dikong.lightcontroller.utils.cmd.ReadWriteEnum;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;

/**
 * @author huangwenjun
 * @version 2018年1月27日 下午5:28:30
 */
@Service
public class CmdServiceImpl implements CmdService {

    private static final String serviceIpKey = "collection.host";

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

    @Autowired
    private JedisPool jedisPool;

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
        return new CmdRes<String>(false, reqResult.getData());
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
        if (results.size() > 0) {
            return new CmdRes<List<String>>(true, results);
        }
        return new CmdRes<List<String>>(false, results);
    }

    @Override
    public CmdRes<List<String>> readMuchVar(List<Register> varIds) {
        if (CollectionUtils.isEmpty(varIds)) {
            return new CmdRes<List<String>>(false, null);
        }

        if (varIds.size() < 2) {
            Register register = varIds.get(0);
            if (Register.BV.equals(register.getRegisType())
                    || Register.BI.equals(register.getRegisType())) {
                return readMuchSwitch(register.getId(), 1);
            } else {
                return readMuchAnalog(register.getId(), 1);
            }
        }

        List<String> results = new ArrayList<>();

        List<Register> tempB = new ArrayList<>();
        List<Register> tempA = new ArrayList<>();
        int size = varIds.size();
        for (int i = 1; i < size; i++) {
            Integer firstAddr = Integer.valueOf(varIds.get(i - 1).getRegisAddr());
            Integer secondAddr = Integer.valueOf(varIds.get(i).getRegisAddr());
            if ((firstAddr + 1) == secondAddr
                    && (Register.BV.equals(varIds.get(i - 1).getRegisType()) || Register.BI
                            .equals(varIds.get(i - 1).getRegisType()))
                    && (Register.BV.equals(varIds.get(i).getRegisType()) || Register.BI
                            .equals(varIds.get(i).getRegisType()))) {
                // 开关连续
                tempB.add(varIds.get(i - 1));
                if (size != (i + 1)) {
                    continue;
                } else {
                    i += 1;
                }
            } else if ((firstAddr + 1) == secondAddr
                    && (Register.AV.equals(varIds.get(i - 1).getRegisType()) || Register.AI
                            .equals(varIds.get(i - 1).getRegisType()))
                    && (Register.AV.equals(varIds.get(i).getRegisType()) || Register.AI
                            .equals(varIds.get(i).getRegisType()))) {
                // 模拟连续
                tempA.add(varIds.get(i - 1));
                if (size != (i + 1)) {
                    continue;
                } else {
                    i += 1;
                }
            }

            if (Register.BV.equals(varIds.get(i - 1).getRegisType())
                    || Register.BI.equals(varIds.get(i - 1).getRegisType())) {
                tempB.add(varIds.get(i - 1));

            } else if (Register.AV.equals(varIds.get(i - 1).getRegisType())
                    || Register.AI.equals(varIds.get(i - 1).getRegisType())) {
                tempA.add(varIds.get(i - 1));
            }
            // 发送命令
            CmdRes<List<String>> listCmdRes = null;
            int tempSize = 0;
            if (tempA.size() == 0) {
                if (Register.DEFAULT_CONNCTION_ADDR.equals(tempB.get(0).getRegisAddr())) {
                    tempB.remove(0);
                }
                listCmdRes = readMuchSwitch(tempB.get(0).getId(), tempB.size());
                tempSize = tempB.size();
                LOG.info("查询多个开关量为{},返回值为{}", tempB, listCmdRes);
            } else if (tempB.size() == 0) {
                listCmdRes = readMuchAnalog(tempA.get(0).getId(), tempA.size());
                tempSize = tempA.size();
                LOG.info("查询多个模拟量为{},返回值为{}", tempA, listCmdRes);
            }
            if (listCmdRes.isSuccess()) {
                results.addAll(listCmdRes.getData());
            } else {
                for (int j = 0; j < tempSize; j++) {
                    results.add(null);
                }
            }
            tempB.removeAll(tempB);
            tempA.removeAll(tempA);
        }
        // 最后一个不能被执行到
        if ((size - 1) == results.size()) {
            Register register = varIds.get(size - 1);
            CmdRes<List<String>> listCmdRes = null;
            if (Register.BV.equals(register.getRegisType())
                    || Register.BI.equals(register.getRegisType())) {
                listCmdRes = readMuchSwitch(register.getId(), 1);
            } else {
                listCmdRes = readMuchAnalog(register.getId(), 1);
            }

            if (listCmdRes.isSuccess()) {
                results.addAll(listCmdRes.getData());
            } else {
                results.add(null);
            }
        }
        return new CmdRes<List<String>>(true, results);
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
        if (register == null) {
            LOG.info("register null");
            return new CmdRes<String>(false, "register null");
        }
        // 根据devId查询串口设备编码
        Device device = deviceDAO.selectDeviceById(register.getDeviceId());
        if (device == null) {
            LOG.info("device null");
            return new CmdRes<String>(false, "device null");
        }
        // 查询DTU信息
        Dtu dtu = dtuDao.selectDtuById(device.getDtuId());
        if (dtu == null) {
            LOG.info("dtu null");
            return new CmdRes<String>(false, "dtu null");
        }
        // 查询一个变量当前值，默认为1
        String sendMsg =
                CmdMsgUtils.assembleSendCmd(device.getCode(), ReadWriteEnum.WRITE,
                        register.getRegisType(), Integer.valueOf(register.getRegisAddr()),
                        switchEnum);
        if (sendMsg == null) {
            return new CmdRes<String>(false, "命令错误！");
        }
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
        String requestId = UUID.randomUUID().toString();
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        try {
            int flag;
            for (flag = 0; flag < Constant.CMD.LOCK_TIME_OUT; flag++) {
                if (!RedisLockUtils.tryGetDistributedLock(jedis, dtu.getDeviceCode(), requestId,
                        Constant.CMD.LOCK_TIME_OUT * 1000)) {
                    Thread.sleep(1000);
                } else {
                    break;
                }
            }
            if (flag == Constant.CMD.LOCK_TIME_OUT) {
                return new CmdRes<String>(false, null);
            }
            response =
                    OkhttpUtils.postFrom(envioroment.getProperty(serviceIpKey) + "/device/command",
                            req, null);
            if (!RedisLockUtils.releaseDistributedLock(jedis, dtu.getDeviceCode(), requestId)) {
                LOG.info("解锁失败！dutCode:" + dtu.getDeviceCode() + " requestId：" + requestId);
            }
        } catch (Exception e) {
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
        if (sendMsg.equals(sendCmdRes.getData().toLowerCase())) {
            return new CmdRes<String>(true, sendCmdRes.getData());
        }
        return new CmdRes<String>(false, sendCmdRes.getData());
    }

    @Override
    public int[] writeSwitch(List<CmdSendDto> allRegis) {
        int[] sendResult = new int[2];
        if (null == allRegis) {
            return sendResult;
        }
        for (CmdSendDto regis : allRegis) {
            CmdRes<String> cmdRes =
                    writeSwitch(regis.getRegisId(), SwitchEnum.getByCode(regis.getSwitchValue()));
            if (cmdRes.isSuccess()) {
                sendResult[0]++;
            } else {
                sendResult[1]++;
            }
        }
        return sendResult;
    }

    @Override
    public CmdRes<String> readOneAnalog(long varId) {
        CmdRes<String> reqResult = reqUtil(varId, ReadWriteEnum.READ, 1);
        if (!reqResult.isSuccess()) {
            return new CmdRes<String>(false, reqResult.getData());
        }
        List<String> results = CmdMsgUtils.analysisAnalogCmd(reqResult.getData());
        if (results.size() > 0) {
            return new CmdRes<String>(true, results.get(0));
        }
        return new CmdRes<String>(false, reqResult.getData());
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
        if (results.size() > 0) {
            return new CmdRes<List<String>>(true, results);
        }
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
        if (register == null) {
            LOG.info("register null");
            return new CmdRes<String>(false, "register null");
        }
        // 根据devId查询串口设备编码
        Device device = deviceDAO.selectDeviceById(register.getDeviceId());
        if (device == null) {
            LOG.info("device null");
            return new CmdRes<String>(false, "device null");
        }
        // 查询DTU信息
        Dtu dtu = dtuDao.selectDtuById(device.getDtuId());
        if (dtu == null) {
            LOG.info("dtu null");
            return new CmdRes<String>(false, "dtu null");
        }
        String requestId = UUID.randomUUID().toString();
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        try {
            int flag;
            for (flag = 0; flag < Constant.CMD.LOCK_TIME_OUT; flag++) {
                if (!RedisLockUtils.tryGetDistributedLock(jedis, dtu.getDeviceCode(), requestId,
                        Constant.CMD.LOCK_TIME_OUT * 1000)) {
                    Thread.sleep(1000);
                } else {
                    break;
                }
            }
            if (flag == Constant.CMD.LOCK_TIME_OUT) {
                return new CmdRes<String>(false, null);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 查询一个变量当前值，默认为1
        CmdRes<String> result =
                reqUtil(dtu, device.getCode(), readWriteEnum, register.getRegisType(),
                        register.getRegisAddr(), varNum);
        if (!RedisLockUtils.releaseDistributedLock(jedis, dtu.getDeviceCode(), requestId)) {
            LOG.info("解锁失败！dutCode:" + dtu.getDeviceCode() + " requestId：" + requestId);
        }
        return result;
    }

    private CmdRes<String> reqUtil(Dtu dtu, String devAddr, ReadWriteEnum readWriteEnum,
            String varType, String varAddr, int varNum) {
        String sendMsg =
                CmdMsgUtils.assembleSendCmd(devAddr, readWriteEnum, varType,
                        Integer.valueOf(varAddr), varNum);
        if (sendMsg == null) {
            return new CmdRes<String>(false, "命令错误！");
        }
        // TODO 命令执行记录
        CmdRecord cmdRecord = new CmdRecord();
        cmdRecord.setDeviceCode(dtu.getDeviceCode());
        cmdRecord.setDevCode(devAddr);
        cmdRecord.setRegisAddr(varAddr);
        cmdRecord.setCmdInfo(sendMsg);
        cmdRecord.setCreateBy(AuthCurrentUser.getUserId());
        String response = "";
        Map<String, String> req = new HashMap<String, String>();
        req.put("cmdType", String.valueOf(readWriteEnum.getCode()));
        req.put("registerMsg", dtu.getDeviceCode());
        req.put("cmd", sendMsg);
        LOG.info("发送信息：" + JSON.toJSONString(req));
        try {
            response =
                    OkhttpUtils.postFrom(envioroment.getProperty(serviceIpKey) + "/device/command",
                            req, null);
        } catch (IOException e) {
            String info = "发送命令异常" + e.toString();
            LOG.error(info);
            e.printStackTrace();
            cmdRecord.setResult(info);
            cmdRecordDao.insert(cmdRecord);
            return new CmdRes<String>(false, "发送命令异常");
        }
        LOG.info("命令发送响应：" + response);
        if (StringUtils.isEmpty(response)
                || StringUtils.isEmpty(response.replace(CmdMsgUtils.strTo16(dtu.getBeatContent()),
                        ""))) {
            String info = "返回值为空或处理之后为空:" + response;
            cmdRecord.setResult(info);
            cmdRecordDao.insert(cmdRecord);
            return new CmdRes<String>(false, info);
        }
        response = response.replace(CmdMsgUtils.strTo16(dtu.getBeatContent()), "");
        SendCmdRes sendCmdRes = JSON.parseObject(response, SendCmdRes.class);
        cmdRecord.setResult(sendCmdRes.getData());
        cmdRecordDao.insert(cmdRecord);
        if (sendCmdRes.getCode() == -1) {
            return new CmdRes<String>(false, sendCmdRes.getData());
        }
        if (readWriteEnum == ReadWriteEnum.WRITE) {
            // 判断是否成功
            if (sendMsg.equals(sendCmdRes.getData().toLowerCase())) {
                return new CmdRes<String>(true, "true");
            } else {
                return new CmdRes<String>(true, "false");
            }
        }
        return new CmdRes<String>(true, sendCmdRes.getData());
    }
}
