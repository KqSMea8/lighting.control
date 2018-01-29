package com.dikong.lightcontroller.schedule;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.service.SysVarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * Description 每天晚上23:59:59扫描开启第二天的定时任务
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午11:14
 * @see </P>
 */
@Component
public class ScanNewTask {

    @Autowired
    private SysVarService sysVarService;

    @Scheduled(cron = "0 0 0 * * *")
    public void scanTimingNewTask(){
        ReturnInfo returnInfo = sysVarService.searchAll();
        if (null != returnInfo.getData() && returnInfo.getData() instanceof List){
            List<SysVar> sysVarList = (List<SysVar>) returnInfo.getData();
            if (!CollectionUtils.isEmpty(sysVarList)){
                for (SysVar sysVar : sysVarList) {
                    if (SysVar.OPEN_SYS_VALUE.equals(sysVar.getVarType())){

                    }
                }
            }
        }
    }
}
