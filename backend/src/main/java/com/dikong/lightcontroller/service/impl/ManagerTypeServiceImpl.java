package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.ManagerTypeDao;
import com.dikong.lightcontroller.dao.ManagerTypeMenuDao;
import com.dikong.lightcontroller.dto.TypeAddMenuReq;
import com.dikong.lightcontroller.entity.ManagerType;
import com.dikong.lightcontroller.entity.ManagerTypeMenu;
import com.dikong.lightcontroller.service.ManagerTypeService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;

import tk.mybatis.mapper.entity.Example;

/**
 * @author huangwenjun
 * @Datetime 2018年1月24日
 */
@Service
public class ManagerTypeServiceImpl implements ManagerTypeService {

    @Autowired
    private ManagerTypeDao managerTypeDao;

    @Autowired
    private ManagerTypeMenuDao managerTypeMenuDao;

    @Override
    public ReturnInfo typeList() {
        Example example = new Example(ManagerType.class);
        example.createCriteria().andEqualTo("isDelete", 1);
        List<ManagerType> managerTypes = managerTypeDao.selectByExample(example);
        if (CollectionUtils.isEmpty(managerTypes)) {
            return ReturnInfo.create(CodeEnum.NOT_CONTENT);
        }
        return ReturnInfo.createReturnSuccessOne(managerTypes);
    }

    @Override
    public ReturnInfo typeAdd(ManagerType managerType) {
        managerType.setCreateBy(AuthCurrentUser.getUserId());
        managerTypeDao.insertSelective(managerType);
        System.out.println("id==" + managerType.getTypeId());
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo typeRemove(int typeId) {
        ManagerType managerType = new ManagerType();
        managerType.setTypeId(typeId);
        managerType.setIsDelete(2);
        managerType.setUpdateBy(AuthCurrentUser.getUserId());
        managerTypeDao.updateByPrimaryKeySelective(managerType);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo typeUpdate(ManagerType managerType) {
        managerType.setUpdateBy(AuthCurrentUser.getUserId());
        managerTypeDao.updateByPrimaryKeySelective(managerType);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo addMenu(TypeAddMenuReq typeAddMenuReq) {
        Example example = new Example(ManagerTypeMenu.class);
        example.createCriteria().andEqualTo("typeId", typeAddMenuReq.getTypeId());
        managerTypeMenuDao.deleteByExample(example);
        Integer userId = AuthCurrentUser.getUserId();
        List<ManagerTypeMenu> managerTypeMenus = new ArrayList<ManagerTypeMenu>();
        for (Integer menuId : typeAddMenuReq.getMenuId()) {
            ManagerTypeMenu managerTypeMenu = new ManagerTypeMenu();
            managerTypeMenu.setTypeId(typeAddMenuReq.getTypeId());
            managerTypeMenu.setMenuId(menuId);
            managerTypeMenu.setCreateBy(userId);
            managerTypeMenus.add(managerTypeMenu);
        }
        managerTypeMenuDao.insertList(managerTypeMenus);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }
}
