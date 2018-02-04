package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.service.HistoryService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.History;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午8:51
 * @see
 *      </P>
 */
@Api(value = "HistoryController",description = "历史资料管理")
@RestController
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping(path = "/history/list/{varId}/{varType}")
    public ReturnInfo historyList(@PathVariable("varId") Long varId,
            @PathVariable("varType") Integer varType) {
        if (!History.REGISTER_TYPE.equals(varType) && !History.GROUP_TYPE.equals(varType)
                && !History.SEQUENCE_TYPE.equals(varType)) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return historyService.searchVarHistory(varId,varType);
    }
}
