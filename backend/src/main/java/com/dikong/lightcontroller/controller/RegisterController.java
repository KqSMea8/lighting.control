package com.dikong.lightcontroller.controller;

import java.util.List;

import com.dikong.lightcontroller.vo.SysVarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.entity.RegisterTime;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.vo.RegisterList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日上午10:17
 * @see
 *      </P>
 */
@Api(value = "RegisterController", description = "变量管理")
@RestController
@RequestMapping("/light")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 设备变量列表
     * 
     * @param registerList
     * @return
     */
    @ApiOperation(value = "查询设备下的所有变量，变量类型可以不传")
    @PostMapping(path = "/register/list")
    public ReturnInfo<List<RegisterTime>> list(@RequestBody RegisterList registerList) {
        return registerService.searchRegister(registerList);
    }

    @DeleteMapping(path = "/register/{id}")
    public ReturnInfo delete(@PathVariable("id") Long id) {
        if (null == id || id == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return registerService.deleteRegister(id);
    }

    @ApiOperation(value = "批量查询变量值")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", required = true),
            @ApiImplicitParam(name = "regisType", required = true)})
    @PostMapping(path = "/register/value")
    public ReturnInfo<List<SysVarList>> regisValue(@RequestBody List<Register> registers) {
        if (CollectionUtils.isEmpty(registers)) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return registerService.regisValue(registers);
    }
}
