package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.vo.HistoryList;
import com.dikong.lightcontroller.vo.HistorySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.service.HistoryService;

import io.swagger.annotations.Api;

import java.util.List;

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
@RequestMapping("/light")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping(path = "/history/list")
    public ReturnInfo<List<HistoryList>> historyList(@RequestBody HistorySearch historySearch) {
        if (!History.REGISTER_TYPE.equals(historySearch.getVarType()) && !History.GROUP_TYPE.equals(historySearch.getVarType())
                && !History.SEQUENCE_TYPE.equals(historySearch.getVarType())) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return historyService.searchVarHistory(historySearch);
    }
}
