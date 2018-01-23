package com.dikong.lightcontroller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.DtuList;
import com.dikong.lightcontroller.entity.Dtu;
import com.dikong.lightcontroller.service.DeviceService;
import com.dikong.lightcontroller.service.DtuService;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午2:24
 * @see
 *      </P>
 */
@RestController
public class DtuController {

    @Autowired
    private DtuService dtuService;

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/dtu/list")
    public ReturnInfo dtuList(@RequestBody DtuList dtuList) {
        return dtuService.list(dtuList);
    }

    @DeleteMapping("/dtu/del/{id}")
    public ReturnInfo delteDtu(@PathVariable("id") Long id) {
        if (null == id || id == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return dtuService.deleteDtu(id);
    }

    @PostMapping("/dtu/add")
    public ReturnInfo addDtu(@RequestBody Dtu dtu) {
        return dtuService.addDtu(dtu);
    }

    @GetMapping("/dtu/{id}")
    public ReturnInfo deviceList(@PathVariable("id") Long dtuId) {
        if (null == dtuId || dtuId == 0) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return deviceService.list(dtuId);
    }

    /**
     * dtu列表选择框
     * @return
     */
    @GetMapping(path = "/dtu/id/list")
    public ReturnInfo dtuIdList(){
        return dtuService.idList();
    }
}
