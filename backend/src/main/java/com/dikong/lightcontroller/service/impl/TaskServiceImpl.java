package com.dikong.lightcontroller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.dto.QuartzJobDto;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.service.api.TaskServiceApi;
import com.dikong.lightcontroller.vo.CommandSend;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.UUID;

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

    @Autowired
    private Environment environment;

    private TaskServiceApi taskServiceApi;

    // 任务调度回调地址
    private String callBackUrl;


    @Autowired
    private TimingDAO timingDAO;

    public TaskServiceImpl() {
        String host = environment.getProperty("schedule.controller.host");
        if (StringUtils.isEmpty(host)) {
            throw new NullPointerException("schedule.controller.host is not null");
        }
        callBackUrl = environment.getProperty("schedule.controller.callbackurl");
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
    public ReturnInfo addTask(Long id, String taskName,String jsonParams, String cron) {
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
        return addTask(id, uuid,jsonParams, cron);
    }
}
