package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.QuartzJobDto;
import com.dikong.lightcontroller.vo.CommandSend;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月29日下午10:05
 * @see
 *      </P>
 */
public interface TaskService {

    ReturnInfo<Boolean> updateTask(QuartzJobDto quartzJobDto);

    ReturnInfo addDeviceTask(Long id);

    ReturnInfo removeDeviceTask(String taskName);

    ReturnInfo<String> addTimingTask(CommandSend commandSend, String cron);

    ReturnInfo removeTimingTask(String taskName);

    ReturnInfo<String> addHolidayTask(String holidayTime);

    ReturnInfo removeHolidayTask(String taskName);

    ReturnInfo addGraphTask(Integer id);

    ReturnInfo removeGraphTask(String taskName);
}
