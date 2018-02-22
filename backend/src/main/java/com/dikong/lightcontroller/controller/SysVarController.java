package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.entity.SysVar;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.SysVarService;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月27日上午11:52
 * @see </P>
 */
@Api(value = "SysVarController",description = "系统变量管理")
@RestController
@RequestMapping("/light")
public class SysVarController {

    @Autowired
    private SysVarService sysVarService;

    @PutMapping(path = "/sys/var/update")
    public ReturnInfo updateSysVar(@RequestBody SysVar sysVar){
        return sysVarService.updateSysVar(sysVar);
    }


    @GetMapping(path = "/sys/var/list/{id}")
    public ReturnInfo varList(@PathVariable("id")Long id){
        return null;
    }



}
