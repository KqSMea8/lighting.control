package com.dikong.lightcontroller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Cnarea2016;
import com.dikong.lightcontroller.service.CnareaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月15日上午8:07
 * @see
 *      </P>
 */
@Api(value = "CnareaController", description = "查询级联地区")
@RestController
@RequestMapping(path = "/light/cnarea")
public class CnareaController {

    @Autowired
    private CnareaService cnareaService;

    @ApiOperation("查询地区")
    @ApiImplicitParams({@ApiImplicitParam(name = "parentId", value = "父id,第一级为0", required = true,
            dataType = "Long", paramType = "path"),})
    @GetMapping(path = "/list/{parentId}")
    public ReturnInfo<List<Cnarea2016>> allCity(@PathVariable("parentId") Long parentId) {
        return cnareaService.allCity(parentId);
    }
}
