package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.History;

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

    ReturnInfo searchVarHistory(Long varId,Integer varType);

    ReturnInfo updateHistory(History history);
}
