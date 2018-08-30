package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.AlarmHistoryList;
import com.dikong.lightcontroller.dto.AlarmList;
import com.dikong.lightcontroller.dto.BasePage;
import com.dikong.lightcontroller.entity.AlarmMessageEntity;
import com.dikong.lightcontroller.entity.AlarmSettingEntity;
import com.dikong.lightcontroller.service.AlarmSettingService;
import com.dikong.lightcontroller.utils.ValidateLogUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 冷荣富
 */
@Api(value = "AlarmSettingController", description = "告警管理")
@RestController
@RequestMapping("/light/alarm")
public class AlarmSettingController {

	private static final Logger LOG = LoggerFactory.getLogger(AlarmSettingController.class);

	@Autowired
	private AlarmSettingService alarmSettingService;

	/**
	 * 添加
	 * @param alarmSettingEntity
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(path = "/add")
	public ReturnInfo<Boolean> add(@Valid @RequestBody AlarmSettingEntity alarmSettingEntity,BindingResult bindingResult){
		if (bindingResult.hasErrors()){
			return ValidateLogUtil.paramError(bindingResult,LOG);
		}
		return alarmSettingService.add(alarmSettingEntity);
	}

	/**
	 * 获取历史
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping(path ="/history")
	public ReturnInfo<List<AlarmHistoryList>> alarmHistory(@RequestParam("pageNo")Integer pageNo,@RequestParam("pageSize")Integer pageSize){
		return alarmSettingService.alarmHistory(pageNo, pageSize);
	}

	/**
	 * 获取告警列表
	 * @param basePage
	 * @return
	 */
	@PostMapping(path = "/list")
	public ReturnInfo<List<AlarmList>> list(@RequestBody BasePage basePage){
		return alarmSettingService.list(basePage);
	}

	/**
	 * 删除告警
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "/del/{id}")
	public ReturnInfo<Boolean> delAlarm(@PathVariable("id")Integer id){
		return alarmSettingService.del(id);
	}

	/**
	 * 获取告警信息
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/{id}")
	public ReturnInfo<AlarmSettingEntity> find(@PathVariable("id")Integer id){
		return alarmSettingService.find(id);
	}

	/**
	 * 更新告警信息
	 * @param entity
	 * @return
	 */
	@PutMapping(path = "/update")
	public ReturnInfo<Boolean> update(@RequestBody AlarmSettingEntity entity){
		return alarmSettingService.update(entity);
	}

	/**
	 * 获取消息列表
	 * @return
	 */
	@PostMapping(path = "/msg")
	public ReturnInfo<List<AlarmMessageEntity>> msg(@RequestBody BasePage basePage){
		return alarmSettingService.msg(basePage);
	}

	/**
	 * 更新当个消息查看状态
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/view/{id}")
	public ReturnInfo<Boolean> updateView(@PathVariable("id")Integer id){
		return alarmSettingService.updateView(id);
	}

	/**
	 * 更新所有的消息状态
	 * @return
	 */
	@GetMapping(path = "/viewall")
	public ReturnInfo<Boolean> updateViewAll(){
		return alarmSettingService.updateViewAll();
	}

	/**
	 * 统计消息
	 * @return
	 */
	@GetMapping(path = "/msg/count")
	public ReturnInfo<Integer> msgCount(){
		return alarmSettingService.msgCount();
	}
}
