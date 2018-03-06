package com.dikong.lightcontroller.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.HolidayDAO;
import com.dikong.lightcontroller.dao.TimingCronDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.dto.QuartzJobDto;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.entity.TimingCron;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.service.api.TaskServiceApi;
import com.dikong.lightcontroller.utils.DateToCronUtils;
import com.dikong.lightcontroller.utils.TimeWeekUtils;
import com.dikong.lightcontroller.vo.CommandSend;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月29日下午10:06
 * @see
 *      </P>
 */
@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

    private static final String DEFAULT_DESCRIPTION = "定时任务";
    private static final String DEFAULT_JOB_GROUP = "LIGHT_CONTROLLER_JOB";
    private static final String DEFAULT_TRIGGER_GROUP = "LIGHT_CONTROLLER_TRIGGER";

    // 每五分钟查一次
    private static final String DEFAULT_DEVICE_CRON = "0 */5 * * * ? *";
    private static final String DEFAULT_DEVICE_JOB_GROUP = "LIGHT_DEVICE_STATUS_JOB";
    private static final String DEFAULT_DEVICE_TRIGGER_GROUP = "LIGHT_DEVICE_STATUS_TRIGGER";

    // 节假日job group
    private static final String DEFAULT_HOLIDAY_JOB_GROUO = "LIGHT_HOLIDAY_JOB";


    private TaskServiceApi taskServiceApi;

    // 时序任务调度回调地址
    private String callBackUrl;
    // 设备回调url
    private String deviceCallBackUrl;
    // 节假日回调url
    private String holidayCallbackUrl;


    @Autowired
    private TimingDAO timingDAO;


    @Autowired
    private TimingCronDAO timingCronDAO;


    // 节假日 db
    @Autowired
    private HolidayDAO holidayDAO;


    @Autowired
    private CmdService cmdService;

    public TaskServiceImpl() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String host = properties.getProperty("schedule.controller.host");
        if (StringUtils.isEmpty(host)) {
            throw new NullPointerException("schedule.controller.host is not null");
        }
        callBackUrl = properties.getProperty("schedule.controller.callbackurl");
        if (StringUtils.isEmpty(callBackUrl)) {
            throw new NullPointerException("schedule.controller.callbackurl is not null");
        }
        deviceCallBackUrl = properties.getProperty("schedule.controller.deviceCallBackUrl");
        if (StringUtils.isEmpty(deviceCallBackUrl)) {
            throw new NullPointerException("schedule.controller.deviceCallBackUrl is not null");
        }
        holidayCallbackUrl = properties.getProperty("schedule.controller.holidayCallbackUrl");
        if (StringUtils.isEmpty(holidayCallbackUrl)) {
            throw new NullPointerException("schedule.controller.holidayCallbackUrl is not null");
        }
        this.taskServiceApi = Feign.builder().decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder()).target(TaskServiceApi.class, host);

    }

    private QuartzJobDto createTask(String method, String taskName, String jsonParams, String cron,
            String groupName, String triggerGroup, String description, String callback) {
        QuartzJobDto quartzJobDto = new QuartzJobDto();
        QuartzJobDto.JobDo jobDo = new QuartzJobDto.JobDo();
        jobDo.setDescription(description);
        jobDo.setGroup(groupName);
        String jobName = taskName;
        jobDo.setName(jobName);
        jobDo.setExtInfo(new QuartzJobDto.ExtInfo(method, callback, jsonParams));
        quartzJobDto.setJobDO(jobDo);
        QuartzJobDto.TriggerDos triggerDos = new QuartzJobDto.TriggerDos();
        triggerDos.setCronExpression(cron);
        triggerDos.setDescription(description);
        triggerDos.setGroup(triggerGroup);
        String triggerName = taskName + "_trigger";
        triggerDos.setName(triggerName);
        Set<QuartzJobDto.TriggerDos> triggerDosSet = new HashSet<>();
        triggerDosSet.add(triggerDos);
        quartzJobDto.setTriggerDOs(triggerDosSet);
        boolean addSuccess = taskServiceApi.addTask(quartzJobDto);
        if (addSuccess) {
            return quartzJobDto;
        }
        return null;
    }

    /**
     * 添加设备任务
     * 
     * @param id
     * @return
     */
    @Override
    public ReturnInfo addDeviceTask(Long id) {
        String taskName = UUID.randomUUID().toString();
        String callBack = deviceCallBackUrl + "/" + id;
        QuartzJobDto task = createTask(QuartzJobDto.METHOD_GET, taskName, "", DEFAULT_DEVICE_CRON,
                DEFAULT_DEVICE_JOB_GROUP, DEFAULT_TRIGGER_GROUP, DEFAULT_DESCRIPTION, callBack);
        LOG.info("添加设置状态命令成功,返回值为{}",JSON.toJSONString(task));
        return ReturnInfo.createReturnSuccessOne(task);
    }

    @Override
    public ReturnInfo removeDeviceTask(String taskName) {
        boolean delTask = removeTask(taskName, DEFAULT_DEVICE_JOB_GROUP);
        LOG.info("删除设置状态命令成功,返回值为{}",delTask);
        return ReturnInfo.createReturnSuccessOne(delTask);
    }

    /**
     *
     * @param commandSend 发送命令
     * @param cron cron 表达式
     * @return
     */
    @Override
    public ReturnInfo<String> addTimingTask(CommandSend commandSend, String cron) {
        long id = commandSend.getTimingId();
        String uuid = UUID.randomUUID().toString();
        commandSend.setTaskName(uuid);
        String jsonParams = JSON.toJSONString(commandSend);
        QuartzJobDto quartzJobDto = createTask(QuartzJobDto.METHOD_POST, uuid, jsonParams, cron,
                DEFAULT_JOB_GROUP, DEFAULT_TRIGGER_GROUP, DEFAULT_DESCRIPTION, callBackUrl);
        if (null != quartzJobDto) {
            TimingCron timingCron = new TimingCron();
            timingCron.setTimingId(id);
            timingCron.setTaskName(uuid);
            String jsonString = JSON.toJSONString(quartzJobDto);
            timingCron.setCronJson(jsonString);
            timingCronDAO.insertSelective(timingCron);
            LOG.info("添加时序定时任务成功,返回值为{}",jsonString);
        }
        return ReturnInfo.create(uuid);
    }




    @Override
    public ReturnInfo removeTimingTask(String taskName) {
        boolean delTask = removeTask(taskName, DEFAULT_JOB_GROUP);
        return ReturnInfo.createReturnSuccessOne(delTask);
    }

    /**
     * 添加节假日任务
     * 
     * @param holidayTime
     * @return 返回任务id
     */
    @Override
    public ReturnInfo<String> addHolidayTask(String holidayTime) {
        String taskName = UUID.randomUUID().toString();
        holidayTime = holidayTime + " 00:00:00";
        String cronFormt = DateToCronUtils.cronFormt(holidayTime);
        holidayCallbackUrl = holidayCallbackUrl + "/" + taskName;
        QuartzJobDto task = createTask(QuartzJobDto.METHOD_GET, taskName, "", cronFormt,
                DEFAULT_HOLIDAY_JOB_GROUO, DEFAULT_TRIGGER_GROUP, "节假日定时任务", holidayCallbackUrl);
        if (null == task) {
            throw new NullPointerException("添加节假日定时任务失败");
        }
        return ReturnInfo.create(taskName);
    }

    /**
     * 删除节假日任务
     * 
     * @param taskName
     * @return
     */
    @Override
    public ReturnInfo removeHolidayTask(String taskName) {
        boolean removeTask = removeTask(taskName, DEFAULT_HOLIDAY_JOB_GROUO);
        return ReturnInfo.createReturnSuccessOne(removeTask);
    }

    private boolean removeTask(String taskName, String jobGroup) {
        Map<String, List<String>> jobKeyGroups = new HashMap<>();
        List<String> names = new ArrayList<>();
        names.add(taskName);
        jobKeyGroups.put(jobGroup, names);
        return taskServiceApi.delTask(jobKeyGroups);
    }
}
