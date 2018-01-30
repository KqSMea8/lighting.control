package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.vo.CommandSend;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月29日下午10:05
 * @see </P>
 */
public interface TaskService {

    ReturnInfo addTask(Long id,String taskName,String jsonParams,String cron);

    ReturnInfo addTask(CommandSend commandSend,String cron);

    ReturnInfo callBack(CommandSend commandSend);

    ReturnInfo removeTask(String taskName);
}
