package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.GroupDeviceMiddleDAO;
import com.dikong.lightcontroller.dao.SysVarDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.dto.CmdSendDto;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.service.HistoryService;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.service.SysVarService;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.DateToCronUtils;
import com.dikong.lightcontroller.utils.TimeWeekUtils;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;
import com.dikong.lightcontroller.vo.CommandSend;

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
    private TaskService taskService;


    @Autowired
    private GroupDeviceMiddleDAO groupDeviceMiddleDAO;

    @Autowired
    private CmdService cmdService;


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
    public ReturnInfo deleteSysVar(Long varId, Integer sysVarType) {
        sysVarDAO.delete(varId, sysVarType);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo searchAll() {
        return ReturnInfo.createReturnSuccessOne(sysVarDAO.selectAll(SysVar.SEQUENCE));
    }

    @Override
    @Transactional
    public ReturnInfo updateSysVar(SysVar sysVar) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        if (SysVar.SEQUENCE.equals(sysVar.getSysVarType())) {
            sysVarDAO.updateSysVar(sysVar.getVarValue(), sysVar.getId());
            processSequence(projId, sysVar.getVarValue());
        } else if (SysVar.GROUP.equals(sysVar.getSysVarType())) {
            sysVarDAO.updateSysVar(sysVar.getVarValue(), sysVar.getId());
            processGroup(sysVar.getVarId(), sysVar.getVarValue());
        } else {
            // 变量类型,值直接修改变量
            registerService.updateRegisterValue(sysVar.getVarId(), sysVar.getVarValue());
            processRegis(sysVar.getVarId(), sysVar.getVarValue());
        }
        // 判断是否需要记录历史表
        historyService.updateHistory(sysVar);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    /**
     * 生成所有的crontable
     * 
     * @param projId 项目id
     * @param value 变量值
     */
    private void processSequence(int projId, String value) {
        // 指定节点
        Example example = new Example(Timing.class);
        example.createCriteria().andEqualTo("isDelete", Timing.DEL_NO).andEqualTo("projId", projId);
        List<Timing> timings = timingDAO.selectByExample(example);
        if (SysVar.OPEN_SYS_VALUE.equals(value) && !CollectionUtils.isEmpty(timings)) {
            for (Timing item : timings) {
                if (Timing.ORDINARY_NODE.equals(item.getNodeType())) {
                    String weekList = item.getWeekList();
                    String nodeContentRunTime = item.getNodeContentRunTime();
                    String cron = DateToCronUtils.cronFormtHHssMM(nodeContentRunTime, weekList);
                    CommandSend commandSend = new CommandSend();
                    commandSend.setTimingId(item.getId());
                    commandSend.setVarIdS(seachAllRegisId(item, value));
                    commandSend.setProjId(projId);
                    taskService.addTimingTask(commandSend, cron);
                } else if (Timing.SPECIFIED_NODE.equals(item.getNodeType())) {
                    String monthList = item.getMonthList();
                    if (null != monthList && !monthList.isEmpty()) {
                        String[] month = monthList.split(",");
                        for (String ymd : month) {
                            String date = ymd + " " + item.getNodeContentRunTime();
                            String cron = DateToCronUtils.cronFormt(date);
                            CommandSend commandSend = new CommandSend();
                            commandSend.setTimingId(item.getId());
                            commandSend.setVarIdS(seachAllRegisId(item, value));
                            commandSend.setProjId(projId);
                            taskService.addTimingTask(commandSend, cron);
                        }
                    }
                }
            }

            // 马上执行最近的时间点,
            String weekNowDate = TimeWeekUtils.getWeekNowDate();
            String yearMonthDay = TimeWeekUtils.getNowDateYearMonthDay();
            Timing timing = timingDAO.selectLastOne(weekNowDate, yearMonthDay);
            if (null != timing) {
                List<CmdSendDto> thenRunRegis = seachAllRegisId(timing, value);
                cmdService.writeSwitch(thenRunRegis);
            }
        } else if (SysVar.CLOSE_SYS_VALUE.equals(value) && !CollectionUtils.isEmpty(timings)) {
            List<CmdSendDto> allRegis = new ArrayList<>();
            timings.forEach(item -> {
                List<CmdSendDto> regisId = seachAllRegisId(item, value);
                allRegis.addAll(regisId);
            });
            cmdService.writeSwitch(allRegis);
        }
        return;
    }



    /**
     * 通过时序查找所有关联的变量id
     * 
     * @param timing
     * @return
     */
    private List<CmdSendDto> seachAllRegisId(Timing timing, String value) {
        List<CmdSendDto> cmdSendDtoList = new ArrayList<>();
        if (Timing.DEVICE_TYPE.equals(timing.getRunType())) {
            if (SysVar.CLOSE_SYS_VALUE.equals(value)) {
                cmdSendDtoList.add(new CmdSendDto(timing.getRunVar(), SwitchEnum.CLOSE.getCode()));
            } else {
                cmdSendDtoList.add(
                        new CmdSendDto(timing.getRunVar(), Integer.valueOf(timing.getRunVarlue())));
            }
        } else if (Timing.GROUP_TYPE.equals(timing.getRunType())) {
            Long groupId = timing.getRunId();
            List<Long> regisIds = groupDeviceMiddleDAO.selectAllRegisId(groupId);
            if (!CollectionUtils.isEmpty(regisIds)) {
                regisIds.forEach(item -> {
                    if (SysVar.CLOSE_SYS_VALUE.equals(value)) {
                        cmdSendDtoList.add(new CmdSendDto(item, SwitchEnum.CLOSE.getCode()));
                    } else {
                        cmdSendDtoList
                                .add(new CmdSendDto(item, Integer.valueOf(timing.getRunVarlue())));
                    }
                });
            }
        }
        return cmdSendDtoList;
    }

    /**
     *
     * @param groupId 群组id
     * @param value 变量值
     */
    private void processGroup(long groupId, String value) {
        List<CmdSendDto> cmdSendDtoList = new ArrayList<>();
        List<Long> regisIds = groupDeviceMiddleDAO.selectAllRegisId(groupId);
        if (!CollectionUtils.isEmpty(regisIds)) {
            regisIds.forEach(item -> {
                if (SysVar.CLOSE_SYS_VALUE.equals(value)) {
                    cmdSendDtoList.add(new CmdSendDto(item, SwitchEnum.CLOSE.getCode()));
                } else {
                    cmdSendDtoList.add(new CmdSendDto(item, SwitchEnum.OPEN.getCode()));
                }
            });
        }
        cmdService.writeSwitch(cmdSendDtoList);
    }

    private void processRegis(long regisId, String value) {
        List<CmdSendDto> cmdSendDtoList = new ArrayList<>();
        if (SysVar.CLOSE_SYS_VALUE.equals(value)) {
            cmdSendDtoList.add(new CmdSendDto(regisId, SwitchEnum.CLOSE.getCode()));
        } else {
            cmdSendDtoList.add(new CmdSendDto(regisId, SwitchEnum.OPEN.getCode()));
        }
        cmdService.writeSwitch(cmdSendDtoList);
    }

}
