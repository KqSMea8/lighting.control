package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.HolidayDAO;
import com.dikong.lightcontroller.dao.SysVarDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.service.HistoryService;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.service.SysVarService;
import com.dikong.lightcontroller.utils.TimeWeekUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午11:03
 * @see
 *      </P>
 */
@Service
public class SysVarServiceImpl implements SysVarService {

    @Autowired
    private SysVarDAO sysVarDAO;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private TimingDAO timingDAO;

    @Autowired
    private HolidayDAO holidayDAO;

    @Override
    public ReturnInfo addSysVarWherNotExist(SysVar sysVar) {
        Integer exisSysVar = sysVarDAO.selectExisSysVar(sysVar.getProjId(), sysVar.getSysVarType());
        if (null == exisSysVar || exisSysVar == 0) {
            sysVarDAO.insertSysVar(sysVar);
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo addSysVar(SysVar sysVar) {
        sysVarDAO.insertSysVar(sysVar);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo deleteSysVar(Long varAddr, Integer sysVarType) {
        sysVarDAO.delete(varAddr, sysVarType);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo searchAll() {
        return ReturnInfo.createReturnSuccessOne(sysVarDAO.selectAll(SysVar.SEQUENCE));
    }

    @Override
    public ReturnInfo updateSysVar(SysVar sysVar) {
        int projId = 0;
        if (SysVar.SEQUENCE.equals(sysVar.getSysVarType())) {
            sysVarDAO.updateSysVar(sysVar.getVarValue(), sysVar.getId());
            processSequence(projId, sysVar.getVarValue());
        } else if (SysVar.GROUP.equals(sysVar.getSysVarType())) {
            sysVarDAO.updateSysVar(sysVar.getVarValue(), sysVar.getId());
        } else {
            registerService.updateRegisterValue(sysVar.getVarId(), sysVar.getVarValue());
        }

        History history = new History();
        history.setVarId(sysVar.getVarId());
        if (SysVar.SEQUENCE.equals(sysVar.getSysVarType())) {
            history.setVarType(History.SEQUENCE_TYPE);
        } else if (SysVar.GROUP.equals(sysVar.getSysVarType())) {
            history.setVarType(History.GROUP_TYPE);
        } else {
            history.setVarType(History.REGISTER_TYPE);
        }
        history.setVarValue(sysVar.getVarValue());
        historyService.updateHistory(history);
        return null;
    }


    private void processSequence(int projId, String value) {
        // 节假日
        String nowDateYearMonthDay = TimeWeekUtils.getNowDateYearMonthDay();
        int todayIsHoliday = holidayDAO.selectTodayIsHoliday(nowDateYearMonthDay, projId);
        if (todayIsHoliday > 0) {
            return;
        }
        // 指定节点
        Example example = new Example(Timing.class);
        example.createCriteria().andEqualTo("nodeType", Timing.SPECIFIED_NODE);
        example.createCriteria().andEqualTo("isDelete", Timing.DEL_NO);
        example.createCriteria().andLike("weekList", "%" + nowDateYearMonthDay + "%");
        example.createCriteria().andEqualTo("projId", projId);
        List<Timing> timings = timingDAO.selectByExample(example);
        if (!CollectionUtils.isEmpty(timings)) {
            timings.forEach(item->{

            });
            return;
        }
        // 普通节点
        String weekNowDate = TimeWeekUtils.getWeekNowDate();
    }
}
