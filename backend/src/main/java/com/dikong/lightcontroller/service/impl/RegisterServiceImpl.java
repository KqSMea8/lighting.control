package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.entity.RegisterTime;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.vo.RegisterList;

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


    @Autowired
    private RegisterDAO registerDAO;

    /**
     * 查询设备下的所有变量
     * 
     * @param registerList
     * @return
     */
    @Override
    public ReturnInfo<List<RegisterTime>> searchRegister(RegisterList registerList) {
//        PageHelper.startPage(registerList.getPageNo(),registerList.getPageSize());
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
}
