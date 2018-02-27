package com.dikong.lightcontroller.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.SystemConfig;
import com.dikong.lightcontroller.service.SystemService;
import com.dikong.lightcontroller.vo.SystemSearch;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * Description 系统设置
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月24日上午10:50
 * @see
 *      </P>
 */
@Api(value = "SystemController", description = "系统设置")
@RestController
@RequestMapping(path = "/light/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @ApiOperation(value = "添加系統設置類型")
    @GetMapping(path = "/add/type/{typevalue}")
    public ReturnInfo addTypeValue(@PathVariable("typevalue")String typevalue){
        return systemService.addTypeValue(typevalue);
    }

    @GetMapping(path = "/list/typeValue")
    public ReturnInfo listType(){
        return systemService.listType();
    }


    @ApiOperation(value = "添加系统设置")
    @PostMapping(path = "/add")
    public ReturnInfo add(SystemConfig systemConfig) throws IOException {
        return systemService.add(systemConfig,systemConfig.getFile());
    }

    @ApiOperation(value = "获取系统设置")
    @ApiImplicitParam(required = true, dataType = "Integer", paramType = "path")
    @GetMapping(path = "/value/{value_id}")
    public ReturnInfo<SystemConfig> search(@PathVariable("value_id") Integer valueId) {
        return systemService.search(valueId);
    }


    @ApiOperation(value = "获取系统设置列表")
    @PostMapping(path = "/list")
    public ReturnInfo<List<SystemConfig>> list(@RequestBody SystemSearch systemSearch) {
        return systemService.list(systemSearch);
    }


    @DeleteMapping(path = "/del/{id}")
    public ReturnInfo del(@PathVariable("id") Long id) {
        return systemService.del(id);
    }

}
