package com.dikong.lightcontroller.init;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Project;
import com.dikong.lightcontroller.listener.DeviceStatusConsumer;
import com.dikong.lightcontroller.service.DeviceService;
import com.dikong.lightcontroller.service.ProjectService;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.utils.JedisProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月12日上午8:01
 * @see
 *      </P>
 */
@Component
@Order(5)
public class KeyExpiredInit implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(KeyExpiredInit.class);

    @Autowired
    private Subscriber subscriber;


    @Autowired
    private BlockingQueue queue;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Override
    public void run(String... args) throws Exception {
        subscriber.subscriber();
        DeviceStatusConsumer consumer = new DeviceStatusConsumer(queue,deviceService);
        new Thread(consumer).start();
        ReturnInfo<List<Project>> allEnableProj = projectService.findAllEnableProj();
        if (!CollectionUtils.isEmpty(allEnableProj.getData())){
            Jedis jedis = new JedisProxy(jedisPool).createProxy();
            for (Project project : allEnableProj.getData()) {
                String enable = jedis.hget(Constant.PROJECT.PROJECT_CRONTAB,
                        String.valueOf(project.getProjectId()));
                if (StringUtils.isEmpty(enable)){
                    jedis.hset(Constant.PROJECT.PROJECT_CRONTAB,String.valueOf(project.getProjectId()),"enable");
                    taskService.addProjectAlarmTask(project.getProjectId(),10);
                }
            }
        }

    }
}
