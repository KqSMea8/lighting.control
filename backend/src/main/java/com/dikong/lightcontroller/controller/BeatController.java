package com.dikong.lightcontroller.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午1:46
 * @see
 *      </P>
 */
@Api(value = "BeatController", description = "心跳接口")
@RestController
public class BeatController {

    @ApiOperation(value = "心跳接口", notes = "心跳接口")
    @GetMapping(path = "/light/beat")
    public String beat() {
        return "alive";
    }
}
