package com.dikong.lightcontroller.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.PageNation;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.CnareaDAO;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.GroupDAO;
import com.dikong.lightcontroller.dao.HolidayDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dao.TimingDAO;
import com.dikong.lightcontroller.dto.DeviceDtu;
import com.dikong.lightcontroller.entity.Cnarea2016;
import com.dikong.lightcontroller.entity.Holiday;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.service.TimingService;
import com.dikong.lightcontroller.utils.ArraysUtils;
import com.dikong.lightcontroller.utils.TimeCalculate;
import com.dikong.lightcontroller.utils.TimeWeekUtils;
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


    @Autowired
    private CnareaDAO cnareaDAO;

    @Autowired
    private TimingDAO timingDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private RegisterDAO registerDAO;

    @Autowired
    private HolidayDAO holidayDAO;

    @SuppressWarnings("all")
    @Override
    public ReturnInfo addOrdinaryNode(TimeOrdinaryNodeAdd ordinaryNodeAdd) {
        int projId = 0;
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
        timing.setNodeName(ordinaryNodeAdd.getNodeName());
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
        int projId = 0;
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
        timing.setNodeName(timeSpecifiedNodeAdd.getNodeName());
        timing.setRunType(timeSpecifiedNodeAdd.getRunType());
        timing.setRunId(timeSpecifiedNodeAdd.getRunId());
        timing.setRunVar(timeSpecifiedNodeAdd.getRunVar());
        timing.setRunVarlue(timeSpecifiedNodeAdd.getRunVarlue());
        timing.setStopWork(timeSpecifiedNodeAdd.getStopWorkOnHoliday());
        timingDAO.insertSelective(timing);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo deleteNode(Long id) {
        timingDAO.updateDeleteById(id, Timing.DEL_YES);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @SuppressWarnings("all")
    @Override
    public ReturnInfo listNodeType(TimingListSearch timingListSearch) {
        Timing search = new Timing();
        search.setNodeType(timingListSearch.getNodeType());
        search.setIsDelete(Timing.DEL_NO);
        PageHelper.startPage(timingListSearch.getPageNo(), timingListSearch.getPageSize());
        List<Timing> timings = timingDAO.select(search);
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
            if (Timing.ORDINARY_NODE.equals(timingListSearch.getNodeType())) {
                builder.append(item.getWeekList());
            } else if (Timing.SPECIFIED_NODE.equals(timingListSearch.getNodeType())) {
                builder.append(item.getMonthList());
            }
            builder.append(";");
            timingList.setNodeContet(builder.toString());
            timingList.setNodeName(item.getNodeName());
            timingList.setId(item.getId());
            timingLists.add(timingList);
        });
        PageNation pageNation = ReturnInfo.create(timings);
        return ReturnInfo.create(timingLists, pageNation);
    }


    @Override
    public ReturnInfo addHolidayNode(String[] holidayTimes) {
        int projId = 0;
        List<Holiday> holidays = new ArrayList<>();
        for (String holidayTime : holidayTimes) {
            holidays.add(new Holiday(holidayTime, projId));
        }
        holidayDAO.insertList(holidays);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo timingView(String viewTime) throws ParseException {

        List<String> weekTime = TimeWeekUtils.getWeekTime(viewTime);
        List<Holiday> holidays = holidayDAO.selectAllHoliday(weekTime);
        Map<String, String> holidaMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(holidays)) {
            holidays.forEach(item -> holidaMap.put(item.getHolidayTime(), item.getHolidayTime()));
        }
        TimingView timingView = new TimingView();
        int day = 1;
        for (String holidayTime : weekTime) {
            List<TimingList> dayList = new ArrayList<>();
            if (null == holidaMap.get(holidayTime)) {
                List<TimingList> dayOrdinary = searchOrdinary(day, null, Timing.ORDINARY_NODE);
                List<TimingList> daySpecified =
                        searchOrdinary(day, holidayTime, Timing.SPECIFIED_NODE);
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


    @SuppressWarnings("all")
    private List<TimingList> searchOrdinary(int day, String monthTime, int nodeType) {
        Example example = new Example(Timing.class);
        if (Timing.ORDINARY_NODE.equals(nodeType)) {
            example.createCriteria().andEqualTo("nodeType", Timing.ORDINARY_NODE);
            example.createCriteria().andEqualTo("isDelete", Timing.DEL_NO);
            example.createCriteria().andLike("weekList", "%" + day + "%");
        } else {
            example.createCriteria().andEqualTo("nodeType", Timing.SPECIFIED_NODE);
            example.createCriteria().andEqualTo("isDelete", Timing.DEL_NO);
            example.createCriteria().andLike("weekList", "%" + monthTime + "%");
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


}
