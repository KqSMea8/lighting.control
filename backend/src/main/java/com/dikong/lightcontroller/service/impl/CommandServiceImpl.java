package com.dikong.lightcontroller.service.impl;

import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.CommandService;
import com.dikong.lightcontroller.vo.CommandSend;

/**
 * <p>
 * Description 命令发送的service
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午9:05
 * @see </P>
 */
@Service
public class CommandServiceImpl implements CommandService{

    @Override
    public ReturnInfo sendCommand(CommandSend commandSend) {

        return ReturnInfo.create(CodeEnum.SUCCESS);
    }
}
