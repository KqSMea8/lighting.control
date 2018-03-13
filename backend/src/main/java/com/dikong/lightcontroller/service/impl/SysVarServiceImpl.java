package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.PageNation;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.DtuDAO;
import com.dikong.lightcontroller.dao.GroupDeviceMiddleDAO;
import com.dikong.lightcontroller.dao.HolidayDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dao.SysVarDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.dto.CmdSendDto;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.entity.Dtu;
import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.entity.RegisterTime;
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
import com.dikong.lightcontroller.vo.RegisterList;
import com.dikong.lightcontroller.vo.SysVarList;
import com.dikong.lightcontroller.vo.VarListSearch;
import com.github.pagehelper.PageHelper;

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

    private static final Logger LOG = LoggerFactory.getLogger(SysVarServiceImpl.class);

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

    @Autowired
    private RegisterDAO registerDAO;

    @Autowired
    private HolidayDAO holidayDAO;

    @Autowired
    private DtuDAO dtuDAO;

    @Override
    public ReturnInfo addSysVarWherNotExist(BaseSysVar sysVar) {
        Integer exisSysVar = sysVarDAO.selectExisSysVar(sysVar.getProjId(), sysVar.getSysVarType());
        if (null == exisSysVar || exisSysVar == 0) {
            sysVarDAO.insertSysVar(sysVar);
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo addSysVar(BaseSysVar sysVar) {
        sysVarDAO.insertSysVar(sysVar);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo deleteSysVar(Long varId, Integer sysVarType) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        sysVarDAO.delete(varId, sysVarType, projId);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo searchAll() {
        return ReturnInfo.createReturnSuccessOne(sysVarDAO.selectAll(BaseSysVar.SEQUENCE));
    }

    @Override
    @Transactional
    public ReturnInfo updateSysVar(BaseSysVar sysVar) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        if (BaseSysVar.SEQUENCE.equals(sysVar.getSysVarType())) {
            sysVarDAO.updateSysVar(sysVar.getVarValue(), sysVar.getId());
            processSequence(projId, sysVar.getVarValue());
        } else if (BaseSysVar.GROUP.equals(sysVar.getSysVarType())) {
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

    @Override
    public ReturnInfo updateSysVarByDeleteProj(BaseSysVar sysVar) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        Long sysVarId = sysVarDAO.selectSequence(projId, BaseSysVar.SEQUENCE);
        if (sysVarId == null){
            return null;
        }
        sysVar.setId(sysVarId);
        sysVarDAO.updateSysVar(sysVar.getVarValue(), sysVar.getId());
        processSequence(projId, sysVar.getVarValue());
        return null;
    }

    @Override
    public ReturnInfo<List<SysVarList>> selectAllVar(VarListSearch varListSearch) {
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
            register.setRegisType(varListSearch.getRegisType());
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
        if (BaseSysVar.OPEN_SYS_VALUE.equals(value) && !CollectionUtils.isEmpty(timings)) {
            List<String> taskNames = new ArrayList<>();
            try {
                for (Timing item : timings) {
                    ReturnInfo<String> addTimingTask = null;
                    if (Timing.ORDINARY_NODE.equals(item.getNodeType())) {
                        String weekList = item.getWeekList();
                        String nodeContentRunTime = item.getNodeContentRunTime();
                        String cron = DateToCronUtils.cronFormtHHssMM(nodeContentRunTime, weekList);
                        CommandSend commandSend = new CommandSend();
                        commandSend.setTimingId(item.getId());
                        commandSend.setVarIdS(seachAllRegisId(item, value));
                        commandSend.setProjId(projId);
                        addTimingTask = taskService.addTimingTask(commandSend, cron);
                        timingDAO.updateTaskNameByID(item.getId(), addTimingTask.getData());
                        taskNames.add(addTimingTask.getData());
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
                                addTimingTask = taskService.addTimingTask(commandSend, cron);
                                timingDAO.updateTaskNameByID(item.getId(), addTimingTask.getData());
                                taskNames.add(addTimingTask.getData());
                            }
                        }
                    }
                }
                // 如果是指定节假日,就把所有变量都设为0
                // String nowDateYearMonthDay = TimeWeekUtils.getNowDateYearMonthDay();
                // int todayIsHoliday = holidayDAO.selectTodayIsHoliday(nowDateYearMonthDay,
                // projId);

                // 马上执行最近的时间点,
                int amount = 0;
                List<Timing> timingList = null;
                for (; amount < 7; amount++) {
                    String weekNowDate = TimeWeekUtils.getLastWeek(amount);
                    String yearMonthDay = TimeWeekUtils.getLastYMD(amount);
                    timingList = timingDAO.selectLastOne(weekNowDate, yearMonthDay, Timing.DEL_NO,
                            projId);
                    if (!CollectionUtils.isEmpty(timingList)) {
                        break;
                    }
                }
                LOG.info("马上执行最近时间点:{}", JSON.toJSONString(timingList));
                if (!CollectionUtils.isEmpty(timingList)) {
                    Map<Long, Timing> groupTime = new HashMap<>();
                    Map<Long, Timing> deviceTime = new HashMap<>();
                    for (Timing timing : timingList) {
                        if (Timing.GROUP_TYPE.equals(timing.getRunType())) {
                            if (Timing.STOP_NO.equals(timing.getStopWork())) {
                                groupTime.put(timing.getRunId(), timing);
                            }
                        } else if (Timing.DEVICE_TYPE.equals(timing.getRunType())) {
                            if (Timing.STOP_NO.equals(timing.getStopWork())) {
                                deviceTime.put(timing.getRunId(), timing);
                            }
                        }
                    }
                    // 群组
                    for (Map.Entry<Long, Timing> map : groupTime.entrySet()) {
                        List<CmdSendDto> thenRunRegis = seachAllRegisId(map.getValue(), value);
                        cmdService.writeSwitch(thenRunRegis);
                    }
                    // 设备
                    for (Map.Entry<Long, Timing> map : deviceTime.entrySet()) {
                        List<CmdSendDto> thenRunRegis = seachAllRegisId(map.getValue(), value);
                        cmdService.writeSwitch(thenRunRegis);
                    }
                }
                // if (todayIsHoliday > 0) {
                // List<CmdSendDto> allRegis = new ArrayList<>();
                // timings.forEach(item -> {
                // List<CmdSendDto> regisId =
                // seachAllRegisId(item, BaseSysVar.CLOSE_SYS_VALUE);
                // allRegis.addAll(regisId);
                // });
                // cmdService.writeSwitch(allRegis);
                // } else {
                //
                // }
            } catch (Exception e) {
                taskNames.forEach(item -> taskService.removeTimingTask(item));
                throw e;
            }
        } else if (BaseSysVar.CLOSE_SYS_VALUE.equals(value) && !CollectionUtils.isEmpty(timings)) {
            List<CmdSendDto> allRegis = new ArrayList<>();
            timings.forEach(item -> {
                List<CmdSendDto> regisId = seachAllRegisId(item, value);
                allRegis.addAll(regisId);
                taskService.removeTimingTask(item.getTaskName());
                timingDAO.updateTaskNameByID(item.getId(), "");
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
    @Override
    public List<CmdSendDto> seachAllRegisId(Timing timing, String value) {
        List<CmdSendDto> cmdSendDtoList = new ArrayList<>();
        if (Timing.DEVICE_TYPE.equals(timing.getRunType())) {
            if (BaseSysVar.CLOSE_SYS_VALUE.equals(value)) {
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
                    if (BaseSysVar.CLOSE_SYS_VALUE.equals(value)) {
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

    @Override
    public ReturnInfo<List<Dtu>> dtuVarList() {
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<Dtu> dtus = dtuDAO.selectAllDtuId(projId, Dtu.DEL_NO);
        Dtu dtu = new Dtu();
        dtu.setId(new Long(0));
        dtu.setDevice("SYS");
        dtus.add(dtu);
        return ReturnInfo.createReturnSuccessOne(dtus);
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
                if (BaseSysVar.CLOSE_SYS_VALUE.equals(value)) {
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
        if (BaseSysVar.CLOSE_SYS_VALUE.equals(value)) {
            cmdSendDtoList.add(new CmdSendDto(regisId, SwitchEnum.CLOSE.getCode()));
        } else {
            cmdSendDtoList.add(new CmdSendDto(regisId, SwitchEnum.OPEN.getCode()));
        }
        cmdService.writeSwitch(cmdSendDtoList);
    }
}
