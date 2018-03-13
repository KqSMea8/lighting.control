package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.CmdSendDto;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.entity.Dtu;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.vo.CommandSend;
import com.dikong.lightcontroller.vo.SysVarList;
import com.dikong.lightcontroller.vo.VarListSearch;

import java.util.List;

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

    ReturnInfo addSysVarWherNotExist(BaseSysVar sysVar);

    ReturnInfo addSysVar(BaseSysVar sysVar);

    ReturnInfo deleteSysVar(Long varId,Integer sysVarType);

    ReturnInfo searchAll();

    ReturnInfo updateSysVar(BaseSysVar sysVar);

    ReturnInfo updateSysVarByDeleteProj(BaseSysVar sysVar);

    ReturnInfo<List<SysVarList>> selectAllVar(VarListSearch varListSearch);

    List<CmdSendDto> seachAllRegisId(Timing timing, String value);

    ReturnInfo<List<Dtu>> dtuVarList();


}
