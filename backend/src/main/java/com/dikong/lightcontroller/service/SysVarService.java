package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.SysVar;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月26日下午11:02
 * @see </P>
 */
public interface SysVarService {

    ReturnInfo addSysVarWherNotExist(SysVar sysVar);

    ReturnInfo addSysVar(SysVar sysVar);

    ReturnInfo deleteSysVar(Long varAddr,Integer sysVarType);

    ReturnInfo searchAll();

    ReturnInfo updateSysVar(SysVar sysVar);
}
