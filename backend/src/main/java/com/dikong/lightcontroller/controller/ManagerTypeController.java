package com.dikong.lightcontroller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.TypeAddMenuReq;
import com.dikong.lightcontroller.entity.ManagerType;
import com.dikong.lightcontroller.service.ManagerTypeService;

/**
 * @author huangwenjun
 * @Datetime 2018年1月24日
 */
@RestController
@RequestMapping("/light/type")
public class ManagerTypeController {

    @Autowired
    private ManagerTypeService managerTypeService;

    @RequestMapping("/list")
    public ReturnInfo list() {
        return managerTypeService.typeList();
    }

    @PostMapping("/add")
    public ReturnInfo add(@RequestBody ManagerType managerType) {
        managerType.setIsDelete(null);
        return managerTypeService.typeAdd(managerType);
    }

    @GetMapping("/del/{typeId}")
    public ReturnInfo del(@PathVariable("typeId") int typeId) {
        return managerTypeService.typeRemove(typeId);
    }

    @PostMapping("/update")
    public ReturnInfo update(@RequestBody ManagerType managerType) {
        return managerTypeService.typeUpdate(managerType);
    }

    @PostMapping("/menu/add")
    public ReturnInfo addMenu(@RequestBody TypeAddMenuReq typeAddMenuReq) {
        if (typeAddMenuReq.getTypeId() == null
                || CollectionUtils.isEmpty(typeAddMenuReq.getMenuId())) {
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        }
        return managerTypeService.addMenu(typeAddMenuReq);
    }
}
