package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.vo.HistoryList;
import com.dikong.lightcontroller.vo.HistorySearch;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午5:04
 * @see </P>
 */
public interface HistoryService {

    ReturnInfo<List<HistoryList>> searchVarHistory(HistorySearch historySearch);

    ReturnInfo updateHistory(History history);

    ReturnInfo updateHistory(BaseSysVar sysVar);
}
