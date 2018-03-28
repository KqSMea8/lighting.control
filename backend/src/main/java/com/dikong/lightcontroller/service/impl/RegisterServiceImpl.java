package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.PageNation;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.GroupDeviceMiddleDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dao.SysVarDAO;
import com.dikong.lightcontroller.dao.TimingCronDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.dto.CmdRes;
import com.dikong.lightcontroller.dto.CmdSendDto;
import com.dikong.lightcontroller.dto.QuartzJobDto;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.entity.GroupDeviceMiddle;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.entity.RegisterTime;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.entity.TimingCron;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.service.EquipmentMonitorService;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.service.TimingService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.vo.CommandSend;
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

    @Autowired
    private TimingDAO timingDAO;

    @Autowired
    private TimingCronDAO timingCronDAO;

    @Autowired
    private GroupDeviceMiddleDAO groupDeviceMiddleDAO;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TimingService timingService;


    @Autowired
    private EquipmentMonitorService equipmentMonitorService;

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
    @Transactional
    @SuppressWarnings("all")
    public ReturnInfo deleteRegister(Long id) throws Exception {
        registerDAO.deleteRegister(id);

        List<GroupDeviceMiddle> groupDeviceMiddles = groupDeviceMiddleDAO.selectByRegisId(id);
        // 删除群组关联的变量
        groupDeviceMiddleDAO.deleteByRegisId(id);
        // 删除管理的变量
        timingService.deleteNodeByRegisId(id);

        // 删除启动的时序变量中的群组关联的变量
        int projId = AuthCurrentUser.getCurrentProjectId();
        SysVar sysVar = sysVarDAO.selectSequence(projId, BaseSysVar.SEQUENCE);
        if (sysVar == null
                || (sysVar != null && BaseSysVar.CLOSE_SYS_VALUE.equals(sysVar.getVarValue()))) {
            return ReturnInfo.create(CodeEnum.SUCCESS);
        }
        for (GroupDeviceMiddle groupDeviceMiddle : groupDeviceMiddles) {
            List<Timing> timings = timingDAO.selectTimingByGroupId(groupDeviceMiddle.getGroupId(),
                    projId, Timing.DEL_NO);
            for (Timing timing : timings) {
                if (!StringUtils.isEmpty(timing.getTaskName())) {
                    TimingCron timingCron = timingCronDAO.selectAllByTaskName(timing.getTaskName());
                    QuartzJobDto quartzJobDto =
                            JSON.parseObject(timingCron.getCronJson(), QuartzJobDto.class);
                    String jsonParams = quartzJobDto.getJobDO().getExtInfo().getJsonParams();
                    CommandSend commandSend = JSON.parseObject(jsonParams, CommandSend.class);
                    List<CmdSendDto> varIdS = commandSend.getVarIdS();
                    for (int i = 0; i < varIdS.size(); i++) {
                        CmdSendDto varId = varIdS.get(i);
                        if (varId.getRegisId().equals(id)) {
                            varIdS.remove(i);
                        }
                    }
                    commandSend.setVarIdS(varIdS);
                    quartzJobDto.getJobDO().getExtInfo()
                            .setJsonParams(JSON.toJSONString(commandSend));
                    ReturnInfo<Boolean> returnInfo = taskService.updateTask(quartzJobDto);
                    if (!returnInfo.getData()) {
                        LOG.error("添加群组变量到关联时序失败:", JSON.toJSONString(quartzJobDto));
                        throw new Exception("添加群组变量到关联时序失败.");
                    }
                    // 更新
                    String jsonString = JSON.toJSONString(quartzJobDto);
                    timingCron.setCronJson(jsonString);
                    timingCronDAO.updateByPrimaryKey(timingCron);
                }
            }
        }

        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo deleteRegisterByDeviceId(Long deviceId) {
        registerDAO.deleteByDeviceId(deviceId);
        List<Long> regisId = registerDAO.selectByDeviceId(deviceId);
        if (!CollectionUtils.isEmpty(regisId)) {
            equipmentMonitorService.delByVarIds(regisId);
        }
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
                    varList.setVarId(sysVar.getVarId());
                    sysVarLists.add(varList);
                }
                pageNation = ReturnInfo.create(sysVarList);
            }
        } else {

            RegisterList register = new RegisterList(varListSearch.getId());
            List<RegisterTime> registers = registerDAO.selectRegisterById(register);
            // registers.sort(RegisterTime::compareRegisAddr);
            List<Register> registerList = new ArrayList<>();
            registers.forEach(item -> {
                Register r = new Register();
                BeanUtils.copyProperties(item, r);
                registerList.add(r);
            });

            CmdRes<List<String>> listCmdRes = cmdService.readMuchVar(registerList);
            LOG.info("发送读取变量但前值命令返回值为:{}", listCmdRes);

            if (!CollectionUtils.isEmpty(registers)) {
                int i = 0;
                for (RegisterTime reg : registers) {

                    SysVarList varList = new SysVarList();
                    varList.setId(reg.getId());
                    varList.setVarNameView(reg.getVarName());
                    varList.setVarName(reg.getRegisName());
                    varList.setVarType(reg.getRegisType());
                    varList.setVarAddr(reg.getRegisAddr());
                    String data = listCmdRes.getData().get(i);
                    i = i + 1;
                    if (null != data) {
                        reg.setRegisValue(data);
                        registerDAO.updateRegisValueById(data, reg.getId());
                    }
                    varList.setVarValue(reg.getRegisValue());
                    varList.setVarTime(reg.getUpdateTime());
                    varList.setItemType(History.REGISTER_TYPE);
                    varList.setVarId(reg.getId());
                    sysVarLists.add(varList);
                }
                pageNation = ReturnInfo.create(registers);
            }
        }
        return ReturnInfo.create(sysVarLists, pageNation);
    }
}
