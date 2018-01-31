package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.HistoryDAO;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.service.HistoryService;

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
    public ReturnInfo searchVarHistory(Long varId, Integer varType) {
        List<History> histories = historyDAO.selectAllByVarId(varId, varType);
        return ReturnInfo.createReturnSuccessOne(histories);
    }

    @Override
    public ReturnInfo updateHistory(History history) {
        int useId = 0;
        History lastHistory =
                historyDAO.selectLastHistory(history.getVarId(), history.getVarType());
        if (null != history.getVarValue() && (null == lastHistory
                || !history.getVarValue().equals(lastHistory.getVarValue()))) {
            history.setCreateBy(useId);
            historyDAO.insertHistory(history);
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo updateHistory(SysVar sysVar) {
        History history = new History();
        if (SysVar.SEQUENCE.equals(sysVar.getSysVarType())) {
            history.setVarId(SysVar.SEQUENCE_VAR_ID);
            history.setVarType(History.SEQUENCE_TYPE);
        } else if (SysVar.GROUP.equals(sysVar.getSysVarType())) {
            history.setVarId(sysVar.getVarId());
            history.setVarType(History.GROUP_TYPE);
        } else {
            history.setVarId(sysVar.getVarId());
            history.setVarType(History.REGISTER_TYPE);
        }
        history.setVarValue(sysVar.getVarValue());
        updateHistory(history);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }
}
