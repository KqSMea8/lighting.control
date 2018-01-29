package com.dikong.lightcontroller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.SysVarDAO;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.service.HistoryService;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.service.SysVarService;

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
        if (SysVar.SEQUENCE.equals(sysVar.getSysVarType()) ||  SysVar.GROUP.equals(sysVar.getSysVarType())){
            sysVarDAO.updateSysVar(sysVar.getVarValue(),sysVar.getId());
        }else {
            registerService.updateRegisterValue(sysVar.getVarId(),sysVar.getVarValue());
        }

        History history = new History();
        history.setVarId(sysVar.getVarId());
        if (SysVar.SEQUENCE.equals(sysVar.getSysVarType())){
            history.setVarType(History.SEQUENCE_TYPE);
        }else if (SysVar.GROUP.equals(sysVar.getSysVarType())){
            history.setVarType(History.GROUP_TYPE);
        }else {
            history.setVarType(History.REGISTER_TYPE);
        }
        history.setVarValue(sysVar.getVarValue());
        historyService.updateHistory(history);
        return null;
    }
}
