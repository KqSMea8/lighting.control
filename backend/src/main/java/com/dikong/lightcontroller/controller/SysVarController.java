package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.vo.SysVarList;
import com.dikong.lightcontroller.vo.VarListSearch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.SysVarService;

import java.util.List;

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

    @ApiOperation(value = "更新系统变量")
    @PutMapping(path = "/sys/var/update")
    public ReturnInfo updateSysVar(@RequestBody BaseSysVar sysVar){
        return sysVarService.updateSysVar(sysVar);
    }


    @ApiOperation(value = "变量目前值列表,id是设备id,选中sys:0时传0")
    @PostMapping(path = "/sys/var/list")
    public ReturnInfo<List<SysVarList>> varList(@RequestBody VarListSearch varListSearch){
        return sysVarService.selectAllVar(varListSearch);
    }




}
