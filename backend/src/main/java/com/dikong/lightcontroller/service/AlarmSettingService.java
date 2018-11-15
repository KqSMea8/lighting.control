package com.dikong.lightcontroller.service;

import java.util.List;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.AlarmHistoryList;
import com.dikong.lightcontroller.dto.AlarmList;
import com.dikong.lightcontroller.dto.BasePage;
import com.dikong.lightcontroller.entity.AlarmHistoryEntity;
import com.dikong.lightcontroller.entity.AlarmMessageEntity;
import com.dikong.lightcontroller.entity.AlarmSettingEntity;
import com.dikong.lightcontroller.entity.Register;

public interface AlarmSettingService {

	ReturnInfo<Boolean> add(AlarmSettingEntity alarmSettingEntity);

	ReturnInfo<List<AlarmHistoryList>> alarmHistory(Integer pageNo,Integer pageSize);

	ReturnInfo<List<AlarmList>> list(BasePage basePage);

	ReturnInfo<Boolean> del(Integer id);

	ReturnInfo<AlarmSettingEntity> find(Integer id);

	ReturnInfo<Boolean> update(AlarmSettingEntity entity);

	ReturnInfo<String> triggerCallback(Integer projectId);

	void sendAlarm(AlarmSettingEntity entity,Register register);

	void sendAlarmClean(AlarmSettingEntity entity,Register register);

	void saveAlarmHistory(AlarmHistoryEntity entity);

	ReturnInfo<List<AlarmMessageEntity>> msg(BasePage basePage);

	ReturnInfo<Boolean> updateView(Integer id);

	ReturnInfo<Boolean> updateViewAll();

	ReturnInfo<Integer> msgCount();
}
