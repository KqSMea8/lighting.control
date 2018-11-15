package com.dikong.lightcontroller.controller;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.DtuList;
import com.dikong.lightcontroller.entity.Dtu;
import com.dikong.lightcontroller.service.DeviceService;
import com.dikong.lightcontroller.service.DtuService;
import com.dikong.lightcontroller.vo.DeviceList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午2:24
 * @see </P>
 */
@Api(value = "DtuController", description = "Dtu 管理") @RestController @RequestMapping("/light")
public class DtuController {

	@Autowired private DtuService dtuService;

	@Autowired private DeviceService deviceService;

	/**
	 * dtu 列表
	 *
	 * @param dtuList
	 * @return
	 */
	@ApiOperation(value = "dtu 列表") @PostMapping("/dtu/list") public ReturnInfo<List<Dtu>> dtuList(
			@RequestBody DtuList dtuList) {
		return dtuService.list(dtuList);
	}

	@ApiOperation(value = "dtu　列表中不分页") @PostMapping("/dtu/list/nopage")
	public ReturnInfo<List<Dtu>> dtuListNoPage(@RequestBody DtuList dtuList) {
		return dtuService.dtuListNoPage(dtuList);
	}

	/**
	 * dtu 删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping("/dtu/del/{id}") public ReturnInfo delteDtu(@PathVariable("id") Long id) {
		if (null == id || id == 0) {
			return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
		}
		return dtuService.deleteDtu(id);
	}

	/**
	 * dtu 添加
	 *
	 * @param dtu
	 * @return
	 */
	@PostMapping("/dtu/add") public ReturnInfo addDtu(@RequestBody Dtu dtu) {
		return dtuService.addDtu(dtu);
	}

	/**
	 * 获取某个dtu下的所有设备
	 *
	 * @param dtuId
	 * @return
	 */
	@GetMapping("/dtu/{id}") public ReturnInfo<List<DeviceList>> deviceList(
			@PathVariable("id") Long dtuId) {
		if (null == dtuId || dtuId == 0) {
			return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
		}
		return deviceService.list(dtuId);
	}

	/**
	 * dtu列表选择框
	 *
	 * @return
	 */
	@GetMapping(path = "/dtu/id/list") public ReturnInfo<List<Dtu>> dtuIdList() {
		return dtuService.idList();
	}

	/**
	 * 修改dtu信息
	 *
	 * @param dtu
	 * @return
	 */
	@PutMapping("/dtu/update") public ReturnInfo updateDtu(@RequestBody Dtu dtu) {
		return dtuService.updateDtu(dtu);
	}


	/**
	 * 上报dtu设备状态接口
	 *
	 * @param deviceCode dtu 注册码
	 * @param line       在线(2)/离线(0)
	 * @return
	 */
	@PostMapping(path = "/api/dtu/connection/{deviceCode}/{line}") public ReturnInfo online(
			@PathVariable("deviceCode") String deviceCode, @PathVariable("line") Integer line) {
		if (StringUtils.isEmpty(deviceCode) || null == line) {
			return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
		}
		return dtuService.conncationInfo(deviceCode, line);
	}

	/**
	 * 数据收集中心读取所有的未删除的设备注册信息
	 *
	 * @return
	 */
	@GetMapping(path = "/api/dtu/all") public ReturnInfo<List<Dtu>> allDtu() {
		return dtuService.allDtu();
	}



	@ApiOperation(value = "DTU下发RTC到所有设备上")
	@ApiImplicitParam(value = "id", name = "dtu主键", required = true)
	@GetMapping(path = "/dtu/rtcsend/{id}")
	public ReturnInfo<String> dtuSendRTC(@PathVariable("id") Long id) {
		return dtuService.dtuSendRTC(id);
	}

}
