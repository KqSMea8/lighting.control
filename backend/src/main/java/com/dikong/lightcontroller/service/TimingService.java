package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
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

    ReturnInfo<List<TimingList>> listNodeType(TimingListSearch timingListSearch);

    ReturnInfo addHolidayNode(String[] holidayTimes);

    ReturnInfo<TimingView> timingView(String viewTime) throws ParseException;
}
