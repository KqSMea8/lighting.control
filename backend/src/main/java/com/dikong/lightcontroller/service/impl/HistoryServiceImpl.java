package com.dikong.lightcontroller.service.impl;

import java.util.List;

import com.dikong.lightcontroller.common.PageNation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.HistoryDAO;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.service.HistoryService;
import com.dikong.lightcontroller.vo.HistoryList;
import com.dikong.lightcontroller.vo.HistorySearch;
import com.github.pagehelper.PageHelper;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午5:04
 * @see
 *      </P>
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryDAO historyDAO;

    @Override
    public ReturnInfo<List<HistoryList>> searchVarHistory(HistorySearch historySearch) {
        PageHelper.startPage(historySearch.getPageNo(), historySearch.getPageSize());
        List<HistoryList> historyLists = null;
        if (History.REGISTER_TYPE.equals(historySearch.getVarType())) {
            // 设备变量
            historyLists = historyDAO.selectAll(historySearch);
        } else {
            // 群组和时序
            historyLists = historyDAO.selectSysVar(historySearch);
        }
        PageNation pageNation = ReturnInfo.create(historyLists);
        return ReturnInfo.create(historyLists,pageNation);
    }

    @Override
    public ReturnInfo updateHistory(History history) {
        int useId = 0;
        History lastHistory =
                historyDAO.selectLastHistory(history.getVarId(), history.getVarType());
        if (null != history.getVarValue() && (null == lastHistory
                || !history.getVarValue().equals(lastHistory.getVarValue()))) {
            history.setCreateBy(useId);
            history.setVarValue(history.getVarValue());
            historyDAO.insertHistory(history);
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo updateHistory(BaseSysVar sysVar) {
        History history = new History();
        if (BaseSysVar.SEQUENCE.equals(sysVar.getSysVarType())) {
            history.setVarId(sysVar.getId());
            history.setVarType(History.SEQUENCE_TYPE);
        } else if (BaseSysVar.GROUP.equals(sysVar.getSysVarType())) {
            history.setVarId(sysVar.getId());
            history.setVarType(History.GROUP_TYPE);
        } else {
            // 变量id
            history.setVarId(sysVar.getVarId());
            history.setVarType(History.REGISTER_TYPE);
        }
        history.setVarValue(sysVar.getVarValue());
        updateHistory(history);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }
}
