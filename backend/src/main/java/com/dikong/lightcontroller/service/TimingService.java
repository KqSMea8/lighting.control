package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Holiday;
import com.dikong.lightcontroller.entity.Timing;
import com.dikong.lightcontroller.vo.BoardList;
import com.dikong.lightcontroller.vo.CommandSend;
import com.dikong.lightcontroller.vo.TimeOrdinaryNodeAdd;
import com.dikong.lightcontroller.vo.TimeSpecifiedNodeAdd;
import com.dikong.lightcontroller.vo.TimingList;
import com.dikong.lightcontroller.vo.TimingListSearch;
import com.dikong.lightcontroller.vo.TimingView;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月23日下午4:08
 * @see </P>
 */
public interface TimingService {

    ReturnInfo addOrdinaryNode(TimeOrdinaryNodeAdd ordinaryNodeAdd);

    ReturnInfo addSpecifiedNode(TimeSpecifiedNodeAdd timeSpecifiedNodeAdd);

    ReturnInfo deleteNode(Long id);

    ReturnInfo deleteNodeByGroupId(Long groupId);

    ReturnInfo deleteNodeByDeviceId(Long deviceId);

    ReturnInfo<List<TimingList>> listNodeType(TimingListSearch timingListSearch);

    ReturnInfo addHolidayNode(String[] holidayTimes);

    ReturnInfo delHolidayNode(String time);

    ReturnInfo<TimingView> timingView(String viewTime) throws ParseException;

    ReturnInfo<List<BoardList>> boardList();

    ReturnInfo<List<Holiday>> getHoliday(String time);

    ReturnInfo<Timing> getOrdinary(Long id);

    ReturnInfo holidayTask();

    ReturnInfo callBack(CommandSend commandSend);
}
