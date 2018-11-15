package com.dikong.lightcontroller.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dikong.lightcontroller.dto.HistoryExportReq;
import com.dikong.lightcontroller.utils.ExcelView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.PageNation;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.HistoryDAO;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.entity.User;
import com.dikong.lightcontroller.service.HistoryService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.vo.HistoryList;
import com.dikong.lightcontroller.vo.HistorySearch;
import com.github.pagehelper.PageHelper;
import org.springframework.web.servlet.ModelAndView;

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

    private static String[] columns = {"DTU设备", "串口设备名称", "变量名称", "值","发生时间","修改人"};

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
        if (!CollectionUtils.isEmpty(historyLists)) {
            for (HistoryList historyList : historyLists) {
                if (StringUtils.isEmpty(historyList.getCreateBy())) {
                    historyList.setCreateBy(User.SYS_USER_NAME);
                }
            }
        }
        PageNation pageNation = ReturnInfo.create(historyLists);
        return ReturnInfo.create(historyLists, pageNation);
    }

    @Override
    public ReturnInfo updateHistory(History history) {
        int useId = AuthCurrentUser.getUserId();
        history.setCreateBy(useId);
        history.setVarValue(history.getVarValue());
        historyDAO.insertHistory(history);

        // History lastHistory =
        // historyDAO.selectLastHistory(history.getVarId(), history.getVarType());
        // if (null != history.getVarValue() && (null == lastHistory
        // || !history.getVarValue().equals(lastHistory.getVarValue()))) {
        // history.setCreateBy(useId);
        // history.setVarValue(history.getVarValue());
        // historyDAO.insertHistory(history);
        // }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo updateHistory(List<History> histories) {
        if (!CollectionUtils.isEmpty(histories)) {
            histories.forEach(history -> this.updateHistory(history));
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

    @Override
    public ModelAndView exportHistory(HistoryExportReq exportReq) throws IOException {
        //1、导出时间最多只能是三个月
        //2、查询数据
        List<Long> registerIds = new ArrayList<>();
        List<Long> seqIds = new ArrayList<>();
        List<Long> groupIds = new ArrayList<>();
        for (HistoryExportReq.Varble varble : exportReq.getVarbles()) {
            if (History.REGISTER_TYPE.equals(varble.getVarType())) {
                registerIds.add(varble.getVarId());
            }else if (History.GROUP_TYPE.equals(varble.getVarType())){
                groupIds.add(varble.getVarId());
            }else if (History.SEQUENCE_TYPE.equals(varble.getVarType())){
                seqIds.add(varble.getVarId());
            }
        }
        List<HistoryList> historyLists = new ArrayList<>();
        if (!CollectionUtils.isEmpty(registerIds)){
            List<HistoryList> lists =
                    historyDAO.selectAllIn(History.REGISTER_TYPE, registerIds);
            historyLists.addAll(lists);
        }
        if (!CollectionUtils.isEmpty(groupIds)){
            List<HistoryList> groups =
                    historyDAO.selectSysVarIn(History.GROUP_TYPE, groupIds);
            historyLists.addAll(groups);
        }
        if (!CollectionUtils.isEmpty(seqIds)){
            List<HistoryList> seqs =
                    historyDAO.selectSysVarIn(History.SEQUENCE_TYPE, seqIds);
            historyLists.addAll(seqs);
        }
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("headers",columns);
        model.put("results",historyLists);
        return new ModelAndView(new ExcelView(), model);
    }
}
