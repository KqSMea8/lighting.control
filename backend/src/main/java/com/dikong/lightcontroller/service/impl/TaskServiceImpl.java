package com.dikong.lightcontroller.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.HolidayDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.dto.QuartzJobDto;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.service.api.TaskServiceApi;
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

    private static final String DEFAULT_DESCRIPTION = "定时任务";
    private static final String DEFAULT_JOB_GROUP = "LIGHT_CONTROLLER_JOB";
    private static final String DEFAULT_TRIGGER_GROUP = "LIGHT_CONTROLLER_TRIGGER";


    private TaskServiceApi taskServiceApi;

    // 任务调度回调地址
    private String callBackUrl;


    @Autowired
    private TimingDAO timingDAO;


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
        this.taskServiceApi = Feign.builder().decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder()).target(TaskServiceApi.class, host);

    }

    /**
     * 添加任务
     * 
     * @param id timing id
     * @param jsonParams 回调参数
     * @param cron cron表达式
     * @return
     */
    @Override
    public ReturnInfo addTask(Long id, String taskName, String jsonParams, String cron) {
        QuartzJobDto quartzJobDto = new QuartzJobDto();
        QuartzJobDto.JobDo jobDo = new QuartzJobDto.JobDo();
        jobDo.setDescription(DEFAULT_DESCRIPTION);
        jobDo.setGroup(DEFAULT_JOB_GROUP);
        String jobName = taskName;
        jobDo.setName(jobName);
        jobDo.setExtInfo(new QuartzJobDto.ExtInfo(callBackUrl, jsonParams));
        quartzJobDto.setJobDo(jobDo);
        QuartzJobDto.TriggerDos triggerDos = new QuartzJobDto.TriggerDos();
        triggerDos.setCronExpression(cron);
        triggerDos.setDescription(DEFAULT_DESCRIPTION);
        triggerDos.setGroup(DEFAULT_TRIGGER_GROUP);
        String triggerName = taskName + "_trigger";
        triggerDos.setName(triggerName);
        quartzJobDto.setTriggerDOs(triggerDos);
        boolean addSuccess = taskServiceApi.addTask(quartzJobDto);
        if (addSuccess) {
            Timing timing = new Timing();
            timing.setTaskName(jobName);
            timing.setId(id);
            timingDAO.updateByPrimaryKey(timing);
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo addTask(CommandSend commandSend, String cron) {
        long id = commandSend.getTimingId();
        String uuid = UUID.randomUUID().toString();
        commandSend.setTaskName(uuid);
        String jsonParams = JSON.toJSONString(commandSend);
        return addTask(id, uuid, jsonParams, cron);
    }


    @Override
    public ReturnInfo callBack(CommandSend commandSend) {
        int projId = commandSend.getProjId();
        // 先判断是否是节假日
        String nowDateYearMonthDay = TimeWeekUtils.getNowDateYearMonthDay();
        int todayIsHoliday = holidayDAO.selectTodayIsHoliday(nowDateYearMonthDay, projId);
        if (todayIsHoliday > 0) {
            return ReturnInfo.create(CodeEnum.SUCCESS);
        }
        // 判断是否有指定日运行
        Example example = new Example(Timing.class);
        example.createCriteria().andEqualTo("nodeType", Timing.SPECIFIED_NODE);
        example.createCriteria().andEqualTo("isDelete", Timing.DEL_NO);
        example.createCriteria().andLike("monthList", "%" + nowDateYearMonthDay + "%");
        example.createCriteria().andEqualTo("projId", projId);
        List<Timing> timings = timingDAO.selectByExample(example);
        if (!CollectionUtils.isEmpty(timings)) {
            // 有指定日节点
            Timing timing = timingDAO.selectById(commandSend.getTimingId());
            if (Timing.SPECIFIED_NODE.equals(timing.getNodeType())) {
                // 判断当前命令是否是指定
                cmdService.writeSwitch(commandSend.getVarIdS());
            }
            return ReturnInfo.create(CodeEnum.SUCCESS);
        }
        // 普通节点,直接运行
        cmdService.writeSwitch(commandSend.getVarIdS());
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo removeTask(String taskName) {
        Map<String, List<String>> jobKeyGroups = new HashMap<>();
        List<String> names = new ArrayList<>();
        names.add(taskName);
        jobKeyGroups.put(DEFAULT_JOB_GROUP, names);
        boolean delTask = taskServiceApi.delTask(jobKeyGroups);
        return ReturnInfo.createReturnSuccessOne(delTask);
    }
}
