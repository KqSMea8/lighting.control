package com.dikong.lightcontroller.job;

import java.util.Date;

import com.dikong.lightcontroller.service.HelloService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月27日下午3:47
 * @see
 *      </P>
 */
public class CommandSendJob implements Job {

    private String  key;

    private HelloService helloService;

    public CommandSendJob(){

    }


    public void execute(JobExecutionContext context) throws JobExecutionException {
//        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//        String jobSays = dataMap.getString("someProp");
//        System.out.println("someProp:"+jobSays);
//        System.out.println("hello world");
//        System.out.println(new Date());
//        System.out.println("key:"+key);
        helloService.hello();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }
}
