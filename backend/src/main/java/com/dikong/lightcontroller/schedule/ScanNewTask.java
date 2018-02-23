package com.dikong.lightcontroller.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.service.SysVarService;

/**
 * <p>
 * Description 每天晚上23:59:59扫描开启第二天的定时任务
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午11:14
 * @see
 *      </P>
 */
@Component
public class ScanNewTask {

    @Autowired
    private SysVarService sysVarService;

    @Scheduled(cron = "5 0 0 * * *")
    public void scanTimingNewTask() {
        ReturnInfo returnInfo = sysVarService.searchAll();
        if (null != returnInfo.getData() && returnInfo.getData() instanceof List) {
            List<BaseSysVar> sysVarList = (List<BaseSysVar>) returnInfo.getData();
            if (!CollectionUtils.isEmpty(sysVarList)) {
                for (BaseSysVar sysVar : sysVarList) {
                    if (BaseSysVar.OPEN_SYS_VALUE.equals(sysVar.getVarType())) {

                    }
                }
            }
        }
    }
}
