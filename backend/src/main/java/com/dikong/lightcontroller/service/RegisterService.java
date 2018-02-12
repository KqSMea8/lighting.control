package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.vo.RegisterList;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日上午10:58
 * @see </P>
 */
public interface RegisterService {
    ReturnInfo<List<Register>> searchRegister(RegisterList registerList);

    ReturnInfo updateRegister(Register register);

    ReturnInfo updateRegisterValue(Long id,String regisValue);

    ReturnInfo deleteRegister(Long id);
}
