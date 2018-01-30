package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.utils.cmd.SwitchEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.vo.CommandSend;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午9:00
 * @see
 *      </P>
 */
@RestController
public class TaskCallbackController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskCallbackController.class);

    @Autowired
    private CmdService cmdService;

    @PostMapping(path = "/command/send")
    public ReturnInfo commandSend(@RequestBody CommandSend commandSend) {
//        String nowDateYearMonthDay = TimeWeekUtils.getNowDateYearMonthDay();
//        int todayIsHoliday = holidayDAO.selectTodayIsHoliday(nowDateYearMonthDay, projId);
//        if (todayIsHoliday > 0) {
//            return;
//        }

        boolean sentStatus = false;
        if (SwitchEnum.OPEN.getCode() == commandSend.getSwitchEnum()){
            sentStatus = cmdService.writeSwitch(0,SwitchEnum.OPEN );
        }else {
            sentStatus = cmdService.writeSwitch(0,SwitchEnum.CLOSE );
        }
        return ReturnInfo.createReturnSuccessOne(sentStatus);
    }
}
