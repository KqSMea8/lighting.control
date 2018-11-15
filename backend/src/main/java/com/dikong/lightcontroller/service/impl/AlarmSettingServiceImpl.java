package com.dikong.lightcontroller.service.impl;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.PageNation;
import com.dikong.lightcontroller.dao.AlarmHistoryDao;
import com.dikong.lightcontroller.dao.AlarmMessageDao;
import com.dikong.lightcontroller.dao.AlarmSendHistoryDao;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.ProjectDao;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dao.UserProjectDao;
import com.dikong.lightcontroller.dto.AlarmHistoryList;
import com.dikong.lightcontroller.dto.AlarmList;
import com.dikong.lightcontroller.dto.BasePage;
import com.dikong.lightcontroller.dto.CmdRes;
import com.dikong.lightcontroller.dto.MailBean;
import com.dikong.lightcontroller.dto.RegisDeviceDtuName;
import com.dikong.lightcontroller.entity.AlarmHistoryEntity;
import com.dikong.lightcontroller.entity.AlarmMessageEntity;
import com.dikong.lightcontroller.entity.AlarmSendHistoryEntity;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.entity.Project;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.service.MailService;
import com.dikong.lightcontroller.service.TxSmsService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.AlarmSettingDao;
import com.dikong.lightcontroller.entity.AlarmSettingEntity;
import com.dikong.lightcontroller.service.AlarmSettingService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.JedisProxy;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Service
public class AlarmSettingServiceImpl implements AlarmSettingService {

    private static final Logger LOG = LoggerFactory.getLogger(AlarmSettingServiceImpl.class);

    @Autowired
    private AlarmSettingDao alarmSettingDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RegisterDAO registerDAO;

    @Autowired
    private CmdService cmdService;

    @Autowired
    private TxSmsService txSmsService;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private AlarmHistoryDao alarmHistoryDao;

    @Autowired
    private AlarmSendHistoryDao alarmSendHistoryDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private AlarmMessageDao alarmMessageDao;


    @Autowired
    private UserProjectDao userProjectDao;


    /**
     * 添加要做校验 1、间隔时间必须大于刷新周期，如果和刷新周期一样，那就默认给间隔时间加上20s 2、如果是比较区间，两个要用逗号拼接起来，并且规定，小的在左边，大的在右边 3、
     * 
     * @param alarmSettingEntity
     * @return
     */
    @Override
    @Transactional
    public ReturnInfo<Boolean> add(AlarmSettingEntity alarmSettingEntity) {
        int projectId = AuthCurrentUser.getCurrentProjectId();
        alarmSettingEntity.setProjectId(projectId);
        alarmSettingDao.insertSelective(alarmSettingEntity);
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        jedis.hset(Constant.ALARM.ALARM_KEY + String.valueOf(projectId),
                String.valueOf(alarmSettingEntity.getId()), "1");
        return ReturnInfo.create(true);
    }

    /**
     * 查看告警历史
     * 
     * @return
     */
    @Override
    public ReturnInfo<List<AlarmHistoryList>> alarmHistory(Integer pageNo, Integer pageSize) {
        int projectId = AuthCurrentUser.getCurrentProjectId();
        PageHelper.startPage(pageNo, pageSize);
        List<AlarmSettingEntity> alarms = alarmSettingDao.selectByProjId(projectId);
        if (CollectionUtils.isEmpty(alarms)) {
            return ReturnInfo.create(CodeEnum.NOT_CONTENT);
        }
        List<Integer> alarmIds = new ArrayList<>();
        alarms.forEach(item -> alarmIds.add(item.getId()));
        PageNation pageNation = ReturnInfo.create(alarmIds);
        List<AlarmHistoryList> alarmHistoryLists = alarmHistoryDao.selectList(alarmIds);
        return ReturnInfo.create(alarmHistoryLists, pageNation);
    }

    /**
     * 告警管理列表页面
     * 
     * @param basePage
     * @return
     */
    @Override
    public ReturnInfo<List<AlarmList>> list(BasePage basePage) {
        int projectId = AuthCurrentUser.getCurrentProjectId();
        PageHelper.startPage(basePage.getPageNo(), basePage.getPageSize());
        List<AlarmSettingEntity> alarms = alarmSettingDao.selectByProjId(projectId);
        if (CollectionUtils.isEmpty(alarms)) {
            return ReturnInfo.create(CodeEnum.NOT_CONTENT);
        }
        PageNation pageNation = ReturnInfo.create(alarms);
        // Todo 1、数据处理
        List<Integer> registerId = new ArrayList<>();
        List<AlarmList> alarmLists = new ArrayList<>();
        for (AlarmSettingEntity alarm : alarms) {
            AlarmList alarmList = new AlarmList();
            if (Constant.ALARM.eq.equals(alarm.getAlarmCompareType())) {
                alarmList.setAlarmValue(" 值 = " + alarm.getAlarmCompareValue());
            } else if (Constant.ALARM.gt.equals(alarm.getAlarmCompareType())) {
                alarmList.setAlarmValue(" 值 > " + alarm.getAlarmCompareValue());
            } else if (Constant.ALARM.lt.equals(alarm.getAlarmCompareType())) {
                alarmList.setAlarmValue(" 值 < " + alarm.getAlarmCompareValue());
            } else if (Constant.ALARM.between.equals(alarm.getAlarmCompareType())) {
                String[] minAndMax = alarm.getAlarmCompareValue().split(",");
                alarmList.setAlarmValue(minAndMax[0] + " <= 值 <= " + minAndMax[1]);
            } else if (Constant.ALARM.neq.equals(alarm.getAlarmCompareType())) {
                alarmList.setAlarmValue(" 值 != " + alarm.getAlarmCompareValue());
            }
            alarmList.setId(alarm.getId());
            alarmList.setPhoneAlarmContent(alarm.getAlarmCotent());
            alarmList.setRegisterId(alarm.getAlarmRegisterId());
            registerId.add(alarm.getAlarmRegisterId());
        }
        List<RegisDeviceDtuName> regisDeviceDtuNames = registerDAO.selectNameByid(registerId);
        Map<Integer, RegisDeviceDtuName> regisDeviceDtuNameMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(regisDeviceDtuNames)) {
            regisDeviceDtuNames.forEach(item -> regisDeviceDtuNameMap.put(item.getId(), item));
        }
        for (AlarmList alarmList : alarmLists) {
            RegisDeviceDtuName regisDeviceDtuName =
                    regisDeviceDtuNameMap.get(alarmList.getRegisterId());
            if (regisDeviceDtuName != null) {
                alarmList.setRegisterName(regisDeviceDtuName.getRegisterName());
                alarmList.setDeviceName(
                        regisDeviceDtuName.getDtuName() + ":" + regisDeviceDtuName.getDeviceName());
            }
        }
        return ReturnInfo.create(alarmLists, pageNation);
    }

    /**
     * 删除告警
     * 
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ReturnInfo<Boolean> del(Integer id) {
        AlarmSettingEntity entity = new AlarmSettingEntity();
        entity.setId(id);
        alarmSettingDao.deleteByPrimaryKey(entity);
        int projectId = AuthCurrentUser.getCurrentProjectId();
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        jedis.hset(Constant.ALARM.ALARM_KEY + String.valueOf(projectId), String.valueOf(id), "1");
        return ReturnInfo.create(true);
    }

    /**
     * 查看告警
     * 
     * @param id
     * @return
     */
    @Override
    public ReturnInfo<AlarmSettingEntity> find(Integer id) {
        AlarmSettingEntity entity = new AlarmSettingEntity();
        entity.setId(id);
        AlarmSettingEntity entity1 = alarmSettingDao.selectByPrimaryKey(entity);
        return ReturnInfo.create(entity1);
    }

    /**
     * 修改告警
     * 
     * @param entity
     * @return
     */
    @Override
    public ReturnInfo<Boolean> update(AlarmSettingEntity entity) {
        alarmSettingDao.updateByPrimaryKeySelective(entity);
        return ReturnInfo.create(true);
    }

    /**
     * 1.持续正常 pass 2.持续异常 pass 3.异常之后持续正常 pass 4.先异常然后再正常，交替出现 pass 5.已告警之后，开始恢复正常，过一会之后又出现异常 pass
     * 
     * @param projectId
     * @return
     */
    @Override
    public ReturnInfo<String> triggerCallback(Integer projectId) {
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        Map<String, String> alarmAll =
                jedis.hgetAll(Constant.ALARM.ALARM_KEY + String.valueOf(projectId));
        if (CollectionUtils.isEmpty(alarmAll)) {
            return ReturnInfo.create("没有数据");
        }
        long start = System.currentTimeMillis();
        for (String alarmId : alarmAll.keySet()) {
            AlarmSettingEntity entity = new AlarmSettingEntity();
            entity.setId(Integer.parseInt(alarmId));
            entity = alarmSettingDao.selectByPrimaryKey(entity);
            Register register =
                    registerDAO.selectRegisById(entity.getAlarmRegisterId().longValue());
            CmdRes<String> cmdRes = null;
            if (Register.BI.equals(register.getRegisType())
                    || Register.BV.equals(register.getRegisType())) {
                cmdRes = cmdService.readOneSwitch(register.getId());
            } else {
                cmdRes = cmdService.readOneAnalog(register.getId());
            }
            if (!cmdRes.isSuccess() || StringUtils.isEmpty(cmdRes.getData())) {
                continue;
            }
            // 进行比较
            Boolean compare = false;
            if (Constant.ALARM.eq.equals(entity.getAlarmCompareType())) {
                compare = cmdRes.getData().equals(entity.getAlarmCompareValue());
            } else if (Constant.ALARM.gt.equals(entity.getAlarmCompareType())) {
                compare = Integer.parseInt(cmdRes.getData()) > Integer
                        .parseInt(entity.getAlarmCompareValue());
            } else if (Constant.ALARM.lt.equals(entity.getAlarmCompareType())) {
                compare = Integer.parseInt(cmdRes.getData()) < Integer
                        .parseInt(entity.getAlarmCompareValue());
            } else if (Constant.ALARM.between.equals(entity.getAlarmCompareType())) {
                String[] minAndMax = entity.getAlarmCompareValue().split(",");
                Integer min = Integer.parseInt(minAndMax[0]);
                Integer max = Integer.parseInt(minAndMax[1]);
                compare = (Integer.parseInt(cmdRes.getData()) >= min
                        && Integer.parseInt(cmdRes.getData()) <= max);
            } else if (Constant.ALARM.neq.equals(entity.getAlarmCompareType())) {
                compare = Integer.parseInt(cmdRes.getData()) != Integer
                        .parseInt(entity.getAlarmCompareValue());
            }

            // 满足告警条件
            if (compare) {
                // 告警激活中
                if (Constant.ALARM.ALARM.equals(entity.getAlarmStatus())) {
                    LOG.info("告警激活中:{},读取返回值:{}", compare, cmdRes);
                    // 修改回为为触发中
                    if (Constant.ALARM.TRIGGER.equals(entity.getTriggerStatus())) {
                        jedis.del(Constant.ALARM.ALARM_CLEAN_KEY + entity.getId());
                        AlarmSettingEntity alarm = new AlarmSettingEntity();
                        alarm.setId(entity.getId());
                        alarm.setTriggerStatus(Constant.ALARM.NO_TRIGGER);
                        alarmSettingDao.updateByPrimaryKeySelective(alarm);
                    }
                    continue;
                }
                synchronized (this) {
                    Boolean exists = jedis.exists(Constant.ALARM.ALARM_KEY + entity.getId());
                    // 如果当前是告警未激活中，exists 为false
                    if (Constant.ALARM.NO_TRIGGER.equals(entity.getTriggerStatus()) && !exists) {
                        // jedis.del(Constant.ALARM.ALARM_CLEAN_KEY + entity.getId());
                        jedis.expire(Constant.ALARM.ALARM_KEY + entity.getId(),
                                entity.getInterval() * 60);
                        // 在触发激活中
                        AlarmSettingEntity alarm = new AlarmSettingEntity();
                        alarm.setId(entity.getId());
                        alarm.setTriggerStatus(Constant.ALARM.TRIGGER);
                        alarmSettingDao.updateByPrimaryKeySelective(alarm);
                    }
                    // 如果当前是告警激活中，exists 为false,说明持续时间已经满足
                    if (Constant.ALARM.TRIGGER.equals(entity.getTriggerStatus()) && !exists) {
                        // 修改回为为触发中
                        AlarmSettingEntity alarm = new AlarmSettingEntity();
                        alarm.setId(entity.getId());
                        alarm.setAlarmStatus(Constant.ALARM.ALARM);
                        alarm.setTriggerStatus(Constant.ALARM.NO_TRIGGER);
                        alarmSettingDao.updateByPrimaryKeySelective(alarm);
                        // 激活触发告警
                        this.sendAlarm(entity, register);
                        // 存入告警历史表中
                        this.saveAlarmHistory(
                                new AlarmHistoryEntity(entity.getId(), entity.getAlarmRegisterId(),
                                        cmdRes.getData(), Constant.ALARM.ALARM_TYPE));

                    }
                    if (exists) {
                        // 告警持续中
                        LOG.info("告警持续中:{}", compare);
                    }
                }
                continue;

            }
            // 不满足告警条件并且已经告过警
            if (Constant.ALARM.ALARM.equals(entity.getAlarmStatus())) {
                synchronized (this) {
                    Boolean exist = jedis.exists(Constant.ALARM.ALARM_CLEAN_KEY + entity.getId());
                    // 如果当前是消失告警未激活中，exists 为false
                    if (Constant.ALARM.NO_TRIGGER.equals(entity.getTriggerStatus()) && !exist) {
                        // jedis.del(Constant.ALARM.ALARM_KEY + entity.getId());
                        jedis.expire(Constant.ALARM.ALARM_CLEAN_KEY + entity.getId(),
                                entity.getInterval() * 60);
                        // 在触发激活中
                        AlarmSettingEntity alarm = new AlarmSettingEntity();
                        alarm.setId(entity.getId());
                        alarm.setTriggerStatus(Constant.ALARM.TRIGGER);
                        alarmSettingDao.updateByPrimaryKeySelective(alarm);
                    }
                    // 如果当前是消失告警激活中，exists 为false,说明持续时间已经满足
                    if (Constant.ALARM.TRIGGER.equals(entity.getTriggerStatus()) && !exist) {
                        // 修改回为为触发中
                        AlarmSettingEntity alarm = new AlarmSettingEntity();
                        alarm.setId(entity.getId());
                        alarm.setAlarmStatus(Constant.ALARM.CLEAR);
                        alarm.setTriggerStatus(Constant.ALARM.NO_TRIGGER);
                        alarmSettingDao.updateByPrimaryKeySelective(alarm);
                        // 激活触发消失告警
                        this.sendAlarmClean(entity, register);

                        // 保存告警历史
                        this.saveAlarmHistory(
                                new AlarmHistoryEntity(entity.getId(), entity.getAlarmRegisterId(),
                                        cmdRes.getData(), Constant.ALARM.CLEAR_TYPE));
                    }
                    if (exist) {
                        // 告警持续中
                        LOG.info("告警消除持续中:{}", compare);
                    }
                }
                continue;
            }

            // 不满足告警条件，并且还没有激活告警就删除之前触发的
            // 修改回为为触发中
            if (Constant.ALARM.TRIGGER.equals(entity.getTriggerStatus())) {
                jedis.del(Constant.ALARM.ALARM_KEY + entity.getId());
                AlarmSettingEntity alarm = new AlarmSettingEntity();
                alarm.setId(entity.getId());
                alarm.setTriggerStatus(Constant.ALARM.NO_TRIGGER);
                alarmSettingDao.updateByPrimaryKeySelective(alarm);
            }
        }
        LOG.info("告警回调执行时间为:{} ms", System.currentTimeMillis() - start);
        return ReturnInfo.create("success");
    }

    /**
     * 发送激活告警信息
     * 
     * @param entity
     */
    @SuppressWarnings("all")
    @Override
    public void sendAlarm(AlarmSettingEntity entity, Register register) {
        String[] alarmPhone = entity.getAlarmPhone().split(",");
        Device device = deviceDAO.selectDeviceById(register.getDeviceId());
        Project project = projectDao.sleectProjectById(register.getProjId());
        String[] params = {project.getProjectName(), device.getName(), entity.getAlarmCotent()};
        // 开启短信告警
        if (Constant.ALARM.Enable.equals(entity.getEnableAlarmPhone())) {
            ReturnInfo<String> result = txSmsService.sendTxSmsAlarm(alarmPhone, params);
            alarmSendHistoryDao.insertSelective(new AlarmSendHistoryEntity(entity.getId(),
                    JSON.toJSONString(asList(alarmPhone, params)), JSON.toJSONString(result)));
        }
        // 开启语音告警
        if (Constant.ALARM.Enable.equals(entity.getEnableAlarmSound())) {
            ReturnInfo<String> result = txSmsService.sendTxSmsVoiceAlarm(alarmPhone, params);
            alarmSendHistoryDao.insertSelective(new AlarmSendHistoryEntity(entity.getId(),
                    JSON.toJSONString(asList(alarmPhone, params)), JSON.toJSONString(result)));
        }
        // 开启邮件告警
        if (Constant.ALARM.Enable.equals(entity.getEnableAlarmEmail())) {
            MailBean mailBean = new MailBean();
            mailBean.setTo(entity.getAlarmEmail().split(","));
            mailBean.setSubject(mailService.templGennerSubject(params));
            mailBean.setContent(mailService.templGenertCotent(params));
            ReturnInfo<String> result = mailService.sendMail(mailBean);
            alarmSendHistoryDao.insertSelective(new AlarmSendHistoryEntity(entity.getId(),
                    JSON.toJSONString(mailBean), JSON.toJSONString(result)));
        }
        // Todo 存入消息中心表
        this.saveMessage(entity.getProjectId(), mailService.templGenertCotent(params));
    }

    /**
     * 发送清除告警信息
     * 
     * @param entity
     */
    @SuppressWarnings("all")
    @Override
    public void sendAlarmClean(AlarmSettingEntity entity, Register register) {
        String[] alarmPhone = entity.getAlarmPhone().split(",");
        Device device = deviceDAO.selectDeviceById(register.getDeviceId());
        Project project = projectDao.sleectProjectById(register.getProjId());
        String[] params =
                {project.getProjectName(), device.getName(), entity.getClearAlarmCotent()};
        // 开启短信告警
        if (Constant.ALARM.Enable.equals(entity.getEnableAlarmPhone())) {
            ReturnInfo<String> result = txSmsService.sendTxSmsAlarm(alarmPhone, params);
            alarmSendHistoryDao.insertSelective(new AlarmSendHistoryEntity(entity.getId(),
                    JSON.toJSONString(asList(alarmPhone, params)), JSON.toJSONString(result)));
        }
        // 开启语音告警
        if (Constant.ALARM.Enable.equals(entity.getEnableClearSound())) {
            ReturnInfo<String> result = txSmsService.sendTxSmsAlarm(alarmPhone, params);
            alarmSendHistoryDao.insertSelective(new AlarmSendHistoryEntity(entity.getId(),
                    JSON.toJSONString(asList(alarmPhone, params)), JSON.toJSONString(result)));
        }
        // 开启邮件告警
        if (Constant.ALARM.Enable.equals(entity.getEnableAlarmEmail())) {
            MailBean mailBean = new MailBean();
            mailBean.setTo(entity.getAlarmEmail().split(","));
            mailBean.setSubject(mailService.templGennerSubject(params));
            mailBean.setContent(mailService.templGenertCotent(params));
            ReturnInfo<String> result = mailService.sendMail(mailBean);
            alarmSendHistoryDao.insertSelective(new AlarmSendHistoryEntity(entity.getId(),
                    JSON.toJSONString(mailBean), JSON.toJSONString(result)));
        }
        // Todo 存入消息中心表
        this.saveMessage(entity.getProjectId(), mailService.templGenertCotent(params));
    }

    /**
     * 存入消息中心表
     * 
     * @param projectId 项目id
     * @param content 内容
     */
    public void saveMessage(Integer projectId, String content) {

        List<Integer> userIds = userProjectDao.selectUserIdByProjId(projectId);
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        List<AlarmMessageEntity> alarmMessages = new ArrayList<>();
        for (Integer userId : userIds) {
            alarmMessages.add(new AlarmMessageEntity(content, userId, projectId));
        }
        alarmMessageDao.insertList(alarmMessages);
    }

    /**
     * 保存告警历史
     * 
     * @param entity
     */
    public void saveAlarmHistory(AlarmHistoryEntity entity) {
        alarmHistoryDao.insertSelective(entity);
    }

    @Override
    public ReturnInfo<List<AlarmMessageEntity>> msg(BasePage basePage) {
        int userId = AuthCurrentUser.getUserId();
        Example example = new Example(AlarmMessageEntity.class);
        example.createCriteria().andEqualTo("userId",userId);

        PageHelper.startPage(basePage.getPageNo(),basePage.getPageSize());
        List<AlarmMessageEntity> alarmMessageEntities = alarmMessageDao.selectByExample(example);
        return ReturnInfo.createReturnSucces(alarmMessageEntities);
    }

    @Override
    public ReturnInfo<Boolean> updateView(Integer id) {
        alarmMessageDao.updateViewById(id);
        return ReturnInfo.create(true);
    }

    @Override
    public ReturnInfo<Boolean> updateViewAll() {
        int userId = AuthCurrentUser.getUserId();
        Integer projectId = AuthCurrentUser.getCurrentProjectId();
        alarmMessageDao.updateViewAll(userId,projectId);
        return ReturnInfo.create(true);
    }

    @Override
    public ReturnInfo<Integer> msgCount() {
        int userId = AuthCurrentUser.getUserId();
        Integer projectId = AuthCurrentUser.getCurrentProjectId();
        if (userId == 0 ){
            return ReturnInfo.create(0);
        }
        Integer count = alarmMessageDao.countByUserId(userId, projectId);
        return ReturnInfo.create(count);
    }


}
