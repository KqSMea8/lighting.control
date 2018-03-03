package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dikong.lightcontroller.dao.SysVarDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.PageNation;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dto.CmdRes;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.entity.RegisterTime;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.vo.RegisterList;
import com.dikong.lightcontroller.vo.SysVarList;
import com.dikong.lightcontroller.vo.VarListSearch;
import com.github.pagehelper.PageHelper;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日上午10:58
 * @see
 *      </P>
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private RegisterDAO registerDAO;


    @Autowired
    private CmdService cmdService;

    @Autowired
    private SysVarDAO sysVarDAO;

    /**
     * 查询设备下的所有变量
     * 
     * @param registerList
     * @return
     */
    @Override
    public ReturnInfo<List<RegisterTime>> searchRegister(RegisterList registerList) {
        // PageHelper.startPage(registerList.getPageNo(),registerList.getPageSize());
        List<RegisterTime> registers = registerDAO.selectRegisterById(registerList);
        registers.sort(RegisterTime::compareRegisAddr);
        return ReturnInfo.createReturnSuccessOne(registers);
    }

    @Override
    public ReturnInfo updateRegister(Register register) {
        registerDAO.updateByRegisAddrAndDeviceId(register, register.getRegisAddr(),
                register.getDeviceId());
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo updateRegisterValue(Long id, String regisValue) {
        registerDAO.updateRegisValueById(regisValue, id);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo deleteRegister(Long id) {
        registerDAO.deleteRegister(id);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo<List<SysVarList>> regisValue(VarListSearch varListSearch) {
        List<SysVarList> sysVarLists = new ArrayList<>();
        PageHelper.startPage(varListSearch.getPageNo(), varListSearch.getPageSize());
        PageNation pageNation = null;
        if (0 == varListSearch.getId()) {
            // 系统变量
            int projId = AuthCurrentUser.getCurrentProjectId();
            List<SysVar> sysVarList = sysVarDAO.selectAllByProjId(projId);
            if (!CollectionUtils.isEmpty(sysVarList)) {
                for (SysVar sysVar : sysVarList) {
                    SysVarList varList = new SysVarList();
                    varList.setId(sysVar.getId());
                    varList.setVarNameView(sysVar.getVarName());
                    varList.setVarName(sysVar.getVarName());
                    varList.setVarType(sysVar.getVarType());
                    varList.setVarAddr(String.valueOf(sysVar.getId()));
                    varList.setVarValue(sysVar.getVarValue());
                    varList.setVarTime(sysVar.getUpdateTime());
                    varList.setItemType(sysVar.getSysVarType());
                    sysVarLists.add(varList);
                }
                pageNation = ReturnInfo.create(sysVarList);
            }
        } else {
            RegisterList register = new RegisterList(varListSearch.getId());
            List<RegisterTime> registers = registerDAO.selectRegisterById(register);
            registers.sort(RegisterTime::compareRegisAddr);
            if (!CollectionUtils.isEmpty(registers)) {
                for (RegisterTime reg : registers) {
                    SysVarList varList = new SysVarList();
                    varList.setId(reg.getId());
                    varList.setVarNameView(reg.getVarName());
                    varList.setVarName(reg.getRegisName());
                    varList.setVarType(reg.getRegisType());
                    varList.setVarAddr(reg.getRegisAddr());

                    CmdRes<String> readResult = null;
                    if (Register.BI.equals(reg.getRegisType())
                            || Register.BV.equals(reg.getRegisType())) {
                        readResult = cmdService.readOneSwitch(reg.getId());
                    } else {
                        readResult = cmdService.readOneAnalog(reg.getId());
                    }
                    LOG.info("发送读取变量但前值命令返回值为:{}", readResult);
                    if (readResult.isSuccess()) {
                        String data = readResult.getData();
                        reg.setRegisValue(data);
                        registerDAO.updateRegisValueById(data, reg.getId());
                    }
                    varList.setVarValue(reg.getRegisValue());
                    varList.setVarTime(reg.getUpdateTime());
                    varList.setItemType(History.REGISTER_TYPE);
                    sysVarLists.add(varList);
                }
                pageNation = ReturnInfo.create(registers);
            }
        }
        return ReturnInfo.create(sysVarLists, pageNation);
    }
}
