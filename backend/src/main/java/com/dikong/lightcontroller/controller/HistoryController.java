package com.dikong.lightcontroller.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.HistoryExportReq;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.service.HistoryService;
import com.dikong.lightcontroller.vo.HistoryList;
import com.dikong.lightcontroller.vo.HistorySearch;

import io.swagger.annotations.Api;

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

    /**
     * 历史数据导出
     *
     */
    @PostMapping(path = "/history/export")
    public ModelAndView historyExport(@RequestBody HistoryExportReq exportReq,HttpServletResponse response)
            throws IOException {
        return historyService.exportHistory(exportReq);
    }
}
