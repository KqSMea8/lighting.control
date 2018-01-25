package com.dikong.lightcontroller.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.TypeAddMenuReq;
import com.dikong.lightcontroller.entity.ManagerType;

/**
 * @author huangwenjun
 * @Datetime 2018年1月24日
 */
public interface ManagerTypeService {
    public ReturnInfo typeList();

    public ReturnInfo typeAdd(ManagerType managerType);

    public ReturnInfo typeRemove(int typeId);

    public ReturnInfo typeUpdate(ManagerType managerType);

    public ReturnInfo addMenu(@RequestBody TypeAddMenuReq typeAddMenuReq);
}
