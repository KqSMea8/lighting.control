package com.dikong.lightcontroller.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.utils.cmd.SwitchEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.GroupDeviceMiddleDAO;
import com.dikong.lightcontroller.dao.HolidayDAO;
import com.dikong.lightcontroller.dao.SysVarDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.entity.SysVar;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.service.HistoryService;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.service.SysVarService;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.utils.DateToCronUtils;
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
    private HolidayDAO holidayDAO;

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

    /**
     * 生成所有的crontable
     * 
     * @param projId
     * @param value
     */
    private void processSequence(int projId, String value) {
        // 指定节点
        Example example = new Example(Timing.class);
        example.createCriteria().andEqualTo("isDelete", Timing.DEL_NO);
        example.createCriteria().andEqualTo("projId", projId);
        List<Timing> timings = timingDAO.selectByExample(example);
        if (SysVar.OPEN_SYS_VALUE.equals(value)){
            if (!CollectionUtils.isEmpty(timings)) {
                timings.forEach(item -> {
                    if (Timing.ORDINARY_NODE.equals(item.getNodeType())) {
                        String weekList = item.getWeekList();
                        String nodeContentRunTime = item.getNodeContentRunTime();
                        String cron = DateToCronUtils.cronFormtHHssMM(nodeContentRunTime, weekList);
                        CommandSend commandSend = new CommandSend();
                        commandSend.setTimingId(item.getId());
                        commandSend.setVarIdS(seachAllRegisId(item, value));
                        taskService.addTask(commandSend, cron);
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
                                taskService.addTask(commandSend, cron);
                            }
                        }
                    }
                });
            }
        }else if (SysVar.CLOSE_SYS_VALUE.equals(value)){
            
        }
        return;
    }




    /**
     * 通过时序查找所有关联的变量id
     * 
     * @param timing
     * @return
     */
    private Map<Long, Integer> seachAllRegisId(Timing timing, String value) {
        Map<Long, Integer> idAndSwitchValue = new HashMap<>();
        if (Timing.DEVICE_TYPE.equals(timing.getRunType())) {
            if (SysVar.CLOSE_SYS_VALUE.equals(value)){
                idAndSwitchValue.put(timing.getRunVar(), SwitchEnum.CLOSE.getCode());
            }else {
                idAndSwitchValue.put(timing.getRunVar(), Integer.valueOf(timing.getRunVarlue()));
            }
        } else if (Timing.GROUP_TYPE.equals(timing.getRunType())) {
            Long groupId = timing.getRunId();
            List<Long> regisIds = groupDeviceMiddleDAO.selectAllRegisId(groupId);
            if (!CollectionUtils.isEmpty(regisIds)) {
                regisIds.forEach(item -> {
                    if (SysVar.CLOSE_SYS_VALUE.equals(value)){
                        idAndSwitchValue.put(item, SwitchEnum.CLOSE.getCode());
                    }else {
                        idAndSwitchValue.put(item, Integer.valueOf(timing.getRunVarlue()));
                    }
                });
            }
        }
        return idAndSwitchValue;
    }
}
