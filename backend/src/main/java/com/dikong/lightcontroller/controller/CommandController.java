package com.dikong.lightcontroller.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.CommandService;
import com.dikong.lightcontroller.utils.ValidateLogUtil;
import com.dikong.lightcontroller.vo.CommandSend;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午9:00
 * @see </P>
 */
@RestController
public class CommandController {

    private static final Logger LOG = LoggerFactory.getLogger(CommandController.class);

    @Autowired
    private CommandService commandService;

    @PutMapping(path = "/command/send")
    public ReturnInfo commandSend(@RequestBody@Valid CommandSend commandSend,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ValidateLogUtil.paramError(bindingResult,LOG);
        }
        return commandService.sendCommand(commandSend);
    }
}
