package com.dikong.lightcontroller.controller;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.job.CommandSendJob;
import com.dikong.lightcontroller.service.HelloService;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月27日下午3:50
 * @see
 *      </P>
 */
@RestController
public class TriggerController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private HelloService helloService;


    @GetMapping(path = "/schedule/trigger/add/{id}")
    public void addTrigger(@PathVariable("id") String id) throws SchedulerException {
        JobDetail job = newJob(CommandSendJob.class).withIdentity("job" + id, "group1")
                .usingJobData("someProp", "someValue").usingJobData("key", id).build();
        Trigger trigger = newTrigger().withIdentity("myTrigger" + id, "group1").startNow()
                .withSchedule(cronSchedule("*/10 * * * * ? *")).build();
        scheduler.scheduleJob(job, trigger);
    }

    @GetMapping(path = "/scedule/trigger/del/{id}")
    public void delTrigger(@PathVariable("id") String id) throws SchedulerException {
        scheduler.unscheduleJob(TriggerKey.triggerKey("myTrigger" + id, "group1"));
    }
}
