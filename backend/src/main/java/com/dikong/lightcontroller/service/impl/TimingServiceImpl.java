package com.dikong.lightcontroller.service.impl;

import java.text.ParseException;
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

import com.dikong.lightcontroller.common.BussinessCode;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.PageNation;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.CnareaDAO;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.GroupDAO;
import com.dikong.lightcontroller.dao.HolidayDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dao.TimingCronDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.dto.CmdSendDto;
import com.dikong.lightcontroller.dto.DeviceDtu;
import com.dikong.lightcontroller.entity.BaseSysVar;
import com.dikong.lightcontroller.entity.Cnarea2016;
import com.dikong.lightcontroller.entity.Group;
import com.dikong.lightcontroller.entity.Holiday;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.entity.TimingCron;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.service.SysVarService;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.service.TimingService;
import com.dikong.lightcontroller.utils.ArraysUtils;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.TimeCalculate;
import com.dikong.lightcontroller.utils.TimeWeekUtils;
import com.dikong.lightcontroller.vo.BoardList;
import com.dikong.lightcontroller.vo.CommandSend;
import com.dikong.lightcontroller.vo.DeviceBoardList;
import com.dikong.lightcontroller.vo.TimeOrdinaryNodeAdd;
import com.dikong.lightcontroller.vo.TimeSpecifiedNodeAdd;
import com.dikong.lightcontroller.vo.TimingList;
import com.dikong.lightcontroller.vo.TimingListSearch;
import com.dikong.lightcontroller.vo.TimingView;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月23日下午4:08
 * @see
 *      </P>
 */
@Service
public class TimingServiceImpl implements TimingService {

    private static final Logger LOG = LoggerFactory.getLogger(TimingServiceImpl.class);

    @Autowired
    private CnareaDAO cnareaDAO;

    @Autowired
    private TimingDAO timingDAO;

    @Autowired
    private TimingCronDAO timingCronDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private RegisterDAO registerDAO;

    @Autowired
    private HolidayDAO holidayDAO;

    @Autowired
    private SysVarService sysVarService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CmdService cmdService;

    @SuppressWarnings("all")
    @Override
    public ReturnInfo addOrdinaryNode(TimeOrdinaryNodeAdd ordinaryNodeAdd) {
        Long runId = ordinaryNodeAdd.getRunVar();
        if (runId != null) {
            Register register = registerDAO.selectRegisById(runId);
            if (null != register && (Register.AI.equals(register.getRegisType())
                    || Register.AV.equals(register.getRegisType()))) {
                return ReturnInfo.create(BussinessCode.NOADD_SIMULATION.getCode(),
                        BussinessCode.NOADD_SIMULATION.getMsg());
            }
        }
        int projId = AuthCurrentUser.getCurrentProjectId();
        Timing lastNodeName = timingDAO.selectByLastNodeName(projId, Timing.DEL_NO);
        int lastNode = 0;
        if (lastNodeName != null) {
            lastNode = Integer.parseInt(lastNodeName.getNodeName());
            lastNode += 1;
        }
        addSysVar(projId);

        Timing timing = new Timing();
        timing.setNodeType(Timing.ORDINARY_NODE);
        timing.setProjId(projId);
        if (null != ordinaryNodeAdd.getStartTimeType()
                && !Timing.ORDINDRY_TIME.equals(ordinaryNodeAdd.getStartTimeType())) {
            // 计算天亮和天黑时间
            Cnarea2016 cnarea = null;
            if (null != ordinaryNodeAdd.getCityId()) {
                cnarea = cnareaDAO.selectCnarea(ordinaryNodeAdd.getCityId());
            } else {
                cnarea = cnareaDAO.selectCnarea(ordinaryNodeAdd.getProvinceId());
            }
            String startTime = null;
            if (Timing.DAWN_TIME.equals(ordinaryNodeAdd.getStartTimeType())) {
                startTime = TimeCalculate.getDawnTime(cnarea.getLng(), cnarea.getLat());
            } else {
                startTime = TimeCalculate.getSunsetTime(cnarea.getLng(), cnarea.getLat());
            }
            timing.setNodeContentRunTime(startTime);
            // provinceId这个值必须有
            Long provinceId = ordinaryNodeAdd.getProvinceId();
            Long cityId = ordinaryNodeAdd.getCityId();
            String contentCity = String.valueOf(provinceId);
            if (null != cityId) {
                contentCity = contentCity + "_" + String.valueOf(cityId);
            }
            timing.setNodeContentCity(contentCity);
        } else {
            timing.setNodeContentRunTime(ordinaryNodeAdd.getStartTime());
        }
        timing.setNodeContentRunTimeType(ordinaryNodeAdd.getStartTimeType());
        timing.setWeekList(ArraysUtils.toString(ordinaryNodeAdd.getWeekList()));
        timing.setNodeName(String.valueOf(lastNode));
        timing.setRunType(ordinaryNodeAdd.getRunType());
        timing.setRunId(ordinaryNodeAdd.getRunId());
        timing.setRunVar(ordinaryNodeAdd.getRunVar());
        timing.setRunVarlue(ordinaryNodeAdd.getRunVarlue());
        timing.setStopWork(ordinaryNodeAdd.getStopWorkOnHoliday());

        timingDAO.insertSelective(timing);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @SuppressWarnings("all")
    @Override
    public ReturnInfo addSpecifiedNode(TimeSpecifiedNodeAdd timeSpecifiedNodeAdd) {
        Long runId = timeSpecifiedNodeAdd.getRunVar();
        if (runId != null) {
            Register register = registerDAO.selectRegisById(runId);
            if (null != register && (Register.AI.equals(register.getRegisType())
                    || Register.AV.equals(register.getRegisType()))) {
                return ReturnInfo.create(BussinessCode.NOADD_SIMULATION.getCode(),
                        BussinessCode.NOADD_SIMULATION.getMsg());
            }
        }
        int projId = AuthCurrentUser.getCurrentProjectId();
        addSysVar(projId);
        Timing lastNodeName = timingDAO.selectByLastNodeName(projId, Timing.DEL_NO);
        int lastNode = 0;
        if (lastNodeName != null) {
            lastNode = Integer.parseInt(lastNodeName.getNodeName());
            lastNode += 1;
        }
        Timing timing = new Timing();
        timing.setNodeType(Timing.SPECIFIED_NODE);
        timing.setProjId(projId);
        if (null != timeSpecifiedNodeAdd.getStartTimeType()
                && !Timing.ORDINDRY_TIME.equals(timeSpecifiedNodeAdd.getStartTimeType())) {
            // 计算天亮和天黑时间
            Cnarea2016 cnarea = null;
            if (null != timeSpecifiedNodeAdd.getCityId()) {
                cnarea = cnareaDAO.selectCnarea(timeSpecifiedNodeAdd.getCityId());
            } else {
                cnarea = cnareaDAO.selectCnarea(timeSpecifiedNodeAdd.getProvinceId());
            }
            String startTime = null;
            if (Timing.DAWN_TIME.equals(timeSpecifiedNodeAdd.getStartTimeType())) {
                startTime = TimeCalculate.getDawnTime(cnarea.getLng(), cnarea.getLat());
            } else {
                startTime = TimeCalculate.getSunsetTime(cnarea.getLng(), cnarea.getLat());
            }
            timing.setNodeContentRunTime(startTime);
            // provinceId这个值必须有
            Long provinceId = timeSpecifiedNodeAdd.getProvinceId();
            Long cityId = timeSpecifiedNodeAdd.getCityId();
            String contentCity = String.valueOf(provinceId);
            if (null != cityId) {
                contentCity = contentCity + "_" + String.valueOf(cityId);
            }
            timing.setNodeContentCity(contentCity);
        } else {
            timing.setNodeContentRunTime(timeSpecifiedNodeAdd.getStartTime());
        }
        timing.setNodeContentRunTimeType(timeSpecifiedNodeAdd.getStartTimeType());
        timing.setMonthList(ArraysUtils.toString(timeSpecifiedNodeAdd.getMonthList()));
        timing.setNodeName(String.valueOf(lastNode));
        timing.setRunType(timeSpecifiedNodeAdd.getRunType());
        timing.setRunId(timeSpecifiedNodeAdd.getRunId());
        timing.setRunVar(timeSpecifiedNodeAdd.getRunVar());
        timing.setRunVarlue(timeSpecifiedNodeAdd.getRunVarlue());
        timing.setStopWork(timeSpecifiedNodeAdd.getStopWorkOnHoliday());
        timingDAO.insertSelective(timing);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    @Transactional
    public ReturnInfo deleteNode(Long id) {
        timingDAO.updateDeleteById(id, Timing.DEL_YES);
        List<TimingCron> timingCrons = timingCronDAO.selectAllByTimingId(id);
        if (!CollectionUtils.isEmpty(timingCrons)) {
            for (TimingCron timingCron : timingCrons) {
                taskService.removeTimingTask(timingCron.getTaskName());
            }
        }
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @SuppressWarnings("all")
    @Override
    public ReturnInfo<List<TimingList>> listNodeType(TimingListSearch timingListSearch) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        Timing search = new Timing();
        search.setNodeType(timingListSearch.getNodeType());
        search.setIsDelete(Timing.DEL_NO);
        search.setProjId(projId);
        PageHelper.startPage(timingListSearch.getPageNo(), timingListSearch.getPageSize());
        List<Timing> timings = timingDAO.select(search);
        List<TimingList> timingLists = new ArrayList<>();
        if (!CollectionUtils.isEmpty(timings)) {
            timings.forEach(item -> {
                TimingList timingList = new TimingList();
                StringBuilder builder = new StringBuilder();
                Integer runTimeType = item.getNodeContentRunTimeType();
                if (Timing.ORDINDRY_TIME.equals(runTimeType)) {
                    builder.append(item.getNodeContentRunTime());
                    builder.append("; ");
                } else if (Timing.DAWN_TIME.equals(runTimeType)) {
                    builder.append("天亮");
                    builder.append("; ");
                    Cnarea2016 cnarea =
                            cnareaDAO.selectCnarea(Long.parseLong(item.getNodeContentCity()));
                    builder.append(cnarea.getName());
                } else if (Timing.DARK_TIME.equals(runTimeType)) {
                    builder.append("天黑");
                    builder.append("; ");
                    Cnarea2016 cnarea =
                            cnareaDAO.selectCnarea(Long.parseLong(item.getNodeContentCity()));
                    builder.append(cnarea.getName());
                }
                builder.append("; ");
                builder.append("[ ");
                if (Timing.GROUP_TYPE.equals(item.getRunType())) {
                    String groupCode = groupDAO.selectCodeById(item.getRunId());
                    builder.append("SYS:Group" + groupCode);
                } else if (Timing.DEVICE_TYPE.equals(item.getRunType())) {
                    DeviceDtu deviceDtu = deviceDAO.selectById(item.getRunId());
                    String registerAddr = registerDAO.selectById(item.getRunVar());
                    String s = deviceDtu.getDtuName() + ":ID"
                            + Integer.parseInt(deviceDtu.getDeviceCode()) + ":" + registerAddr;
                    builder.append(s);
                }
                builder.append(" ]");
                builder.append("=");
                builder.append(item.getRunVarlue());
                builder.append("; ");
                if (Timing.ORDINARY_NODE.equals(timingListSearch.getNodeType())) {
                    builder.append(item.getWeekList());
                } else if (Timing.SPECIFIED_NODE.equals(timingListSearch.getNodeType())) {
                    builder.append(item.getMonthList());
                }
                builder.append("; ");
                timingList.setNodeContet(builder.toString());
                timingList.setNodeName("节点" + item.getNodeName());
                timingList.setId(item.getId());
                timingLists.add(timingList);
            });
        }
        PageNation pageNation = ReturnInfo.create(timings);
        return ReturnInfo.create(timingLists, pageNation);
    }


    @Override
    @Transactional
    public ReturnInfo addHolidayNode(String[] holidayTimes) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<Holiday> holidays = new ArrayList<>();
        for (String holidayTime : holidayTimes) {
            ReturnInfo<String> stringReturnInfo = taskService.addHolidayTask(holidayTime);
            holidays.add(new Holiday(holidayTime, projId, stringReturnInfo.getData()));
        }
        holidayDAO.insertList(holidays);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo<TimingView> timingView(String viewTime) throws ParseException {
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<String> weekTime = TimeWeekUtils.getWeekTime(viewTime);
        List<Holiday> holidays = holidayDAO.selectAllHoliday(weekTime, projId);
        Map<String, String> holidaMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(holidays)) {
            holidays.forEach(item -> holidaMap.put(item.getHolidayTime(), item.getHolidayTime()));
        }
        TimingView timingView = new TimingView();
        int day = 1;
        for (String holidayTime : weekTime) {
            List<TimingList> dayList = new ArrayList<>();
            if (null == holidaMap.get(holidayTime)) {
                List<TimingList> dayOrdinary =
                        searchOrdinary(projId, day, null, Timing.ORDINARY_NODE);
                List<TimingList> daySpecified =
                        searchOrdinary(projId, day, holidayTime, Timing.SPECIFIED_NODE);
                dayList.addAll(dayOrdinary);
                dayList.addAll(daySpecified);
            }
            if (day == 1) {
                timingView.setMonday(dayList);
            } else if (day == 2) {
                timingView.setTuesday(dayList);
            } else if (day == 3) {
                timingView.setWednesday(dayList);
            } else if (day == 4) {
                timingView.setThursday(dayList);
            } else if (day == 5) {
                timingView.setFriday(dayList);
            } else if (day == 6) {
                timingView.setStaturday(dayList);
            } else if (day == 7) {
                timingView.setSunday(dayList);
            }
            day += 1;
        }
        return ReturnInfo.createReturnSuccessOne(timingView);
    }


    @Override
    public ReturnInfo boardList() {
        List<BoardList> boardLists = new ArrayList<>();
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<DeviceBoardList> deviceBoardLists = deviceDAO.selectNotIn(projId);
        if (!CollectionUtils.isEmpty(deviceBoardLists)) {
            for (DeviceBoardList deviceBoardList : deviceBoardLists) {
                BoardList boardList = new BoardList();
                boardList.setDeviceIdOrGroupId(deviceBoardList.getId());
                boardList.setDtuOrSysName(deviceBoardList.getDtuName());
                boardList.setDeviceOrGroupName(deviceBoardList.getDeviceName());
                boardList.setDeviceCodeOrGroup(
                        Integer.parseInt(deviceBoardList.getDeviceCode()) + "");
                boardList.setDeviceLocation(
                        boardList.getDtuOrSysName() + ":ID" + boardList.getDeviceCodeOrGroup());
                boardList.setItemType(Timing.DEVICE_TYPE);
                boardLists.add(boardList);
            }
        }
        List<Group> groups = groupDAO.selectByProjId(projId);
        if (!CollectionUtils.isEmpty(groups)) {
            for (Group group : groups) {
                BoardList boardList = new BoardList();
                boardList.setDeviceIdOrGroupId(group.getId());
                boardList.setDtuOrSysName("SYS");
                boardList.setDeviceOrGroupName(group.getGroupName());
                boardList.setDeviceCodeOrGroup("Group" + group.getGroupCode());
                boardList.setDeviceLocation(
                        boardList.getDtuOrSysName() + ":" + boardList.getDeviceCodeOrGroup());
                boardList.setItemType(Timing.GROUP_TYPE);
                boardLists.add(boardList);
            }
        }
        return ReturnInfo.createReturnSuccessOne(boardLists);
    }


    @Override
    public ReturnInfo<List<Holiday>> getHoliday(String time) {
        int projId = AuthCurrentUser.getCurrentProjectId();
        List<Holiday> holidays = holidayDAO.selectHoliday(time, projId);
        return ReturnInfo.createReturnSuccessOne(holidays);
    }

    @Override
    public ReturnInfo<Timing> getOrdinary(Long id) {
        timingDAO.selectById(id);
        return null;
    }

    /**
     * 节假日当天关闭所有的变量
     * 
     * @return
     */
    @Override
    public ReturnInfo holidayTask() {
        String weekNowDate = TimeWeekUtils.getWeekNowDate();
        String yearMonthDay = TimeWeekUtils.getNowDateYearMonthDay();
        List<Timing> timingList = timingDAO.selectLastOne(weekNowDate, yearMonthDay);
        List<CmdSendDto> allRegis = new ArrayList<>();
        timingList.forEach(item -> {
            List<CmdSendDto> regisId =
                    sysVarService.seachAllRegisId(item, BaseSysVar.CLOSE_SYS_VALUE);
            allRegis.addAll(regisId);
        });
        cmdService.writeSwitch(allRegis);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    /**
     * 回调执行任务
     *
     * @param commandSend
     * @return
     */
    @Override
    public ReturnInfo callBack(CommandSend commandSend) {
        int projId = commandSend.getProjId();
        // 先判断是否是节假日
        String nowDateYearMonthDay = TimeWeekUtils.getNowDateYearMonthDay();
        int todayIsHoliday = holidayDAO.selectTodayIsHoliday(nowDateYearMonthDay, projId);
        Timing timing = timingDAO.selectById(commandSend.getTimingId());
        if (todayIsHoliday > 0 && null != timing && Timing.STOP_YES.equals(timing.getStopWork())) {
            LOG.info("有指定节假日,{}", nowDateYearMonthDay);
            return ReturnInfo.create(CodeEnum.SUCCESS);
        }
        // 判断是否有指定日运行
        Example example = new Example(Timing.class);
        example.createCriteria().andEqualTo("nodeType", Timing.SPECIFIED_NODE)
                .andEqualTo("isDelete", Timing.DEL_NO).andEqualTo("projId", projId)
                .andLike("monthList", "%" + nowDateYearMonthDay + "%");
        List<Timing> timings = timingDAO.selectByExample(example);
        if (!CollectionUtils.isEmpty(timings)) {
            // 有指定日节点
            LOG.info("有指定日节点,{}", timings.size());
            if (Timing.SPECIFIED_NODE.equals(timing.getNodeType())) {
                // 判断当前命令是否是指定日
                cmdService.writeSwitch(commandSend.getVarIdS());
            }
            return ReturnInfo.create(CodeEnum.SUCCESS);
        }
        // 普通节点,直接运行
        cmdService.writeSwitch(commandSend.getVarIdS());
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @SuppressWarnings("all")
    private List<TimingList> searchOrdinary(int projId, int day, String monthTime, int nodeType) {
        Example example = new Example(Timing.class);
        if (Timing.ORDINARY_NODE.equals(nodeType)) {
            example.createCriteria().andEqualTo("nodeType", Timing.ORDINARY_NODE)
                    .andEqualTo("projId", projId).andEqualTo("isDelete", Timing.DEL_NO)
                    .andLike("weekList", "%" + day + "%");

        } else {
            example.createCriteria().andEqualTo("nodeType", Timing.SPECIFIED_NODE)
                    .andEqualTo("isDelete", Timing.DEL_NO).andEqualTo("projId", projId)
                    .andLike("monthList", "%" + monthTime + "%");
        }
        List<Timing> timings = timingDAO.selectByExample(example);
        List<TimingList> timingLists = new ArrayList<>();
        timings.forEach(item -> {
            TimingList timingList = new TimingList();
            StringBuilder builder = new StringBuilder();
            Integer runTimeType = item.getNodeContentRunTimeType();
            if (Timing.ORDINDRY_TIME.equals(runTimeType)) {
                builder.append(item.getNodeContentRunTime());
                builder.append(";");
            } else if (Timing.DAWN_TIME.equals(runTimeType)) {
                builder.append("天亮");
                builder.append(";");
                Cnarea2016 cnarea =
                        cnareaDAO.selectCnarea(Long.parseLong(item.getNodeContentCity()));
                builder.append(cnarea.getName());
            } else if (Timing.DARK_TIME.equals(runTimeType)) {
                builder.append("天黑");
                builder.append(";");
                Cnarea2016 cnarea =
                        cnareaDAO.selectCnarea(Long.parseLong(item.getNodeContentCity()));
                builder.append(cnarea.getName());
            }
            builder.append(";");
            builder.append("[ ");
            if (Timing.GROUP_TYPE.equals(item.getRunType())) {
                String groupCode = groupDAO.selectCodeById(item.getRunId());
                builder.append("SYS:Group" + groupCode);
            } else if (Timing.DEVICE_TYPE.equals(item.getRunType())) {
                DeviceDtu deviceDtu = deviceDAO.selectById(item.getRunId());
                String registerAddr = registerDAO.selectById(item.getRunVar());
                String s = deviceDtu.getDtuName() + ":ID"
                        + Integer.parseInt(deviceDtu.getDeviceCode()) + ":" + registerAddr;
                builder.append(s);
            }
            builder.append(" ]");
            builder.append("=");
            builder.append(item.getRunVarlue());
            builder.append(";");
            timingList.setNodeContet(builder.toString());
            timingList.setNodeName(item.getNodeName());
            timingList.setId(item.getId());
            timingLists.add(timingList);
        });
        return timingLists;
    }


    private void addSysVar(int projId) {
        BaseSysVar sysVar = new BaseSysVar();
        sysVar.setSysVarType(BaseSysVar.SEQUENCE);
        sysVar.setVarName("Sequence_EN");
        sysVar.setVarId(BaseSysVar.SEQUENCE_VAR_ID);
        sysVar.setVarValue(BaseSysVar.CLOSE_SYS_VALUE);
        sysVar.setProjId(projId);
        sysVarService.addSysVarWherNotExist(sysVar);
    }

}
