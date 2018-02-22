package com.dikong.lightcontroller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.CnareaDAO;
import com.dikong.lightcontroller.entity.Cnarea2016;
import com.dikong.lightcontroller.service.CnareaService;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月15日上午8:21
 * @see
 *      </P>
 */
@Service
public class CnareaServiceImpl implements CnareaService {

    @Autowired
    private CnareaDAO cnareaDAO;

    @Override
    public ReturnInfo<List<Cnarea2016>> allCity(Long parentId) {
        List<Cnarea2016> cnarea2016s = cnareaDAO.selectAllByParentId(parentId);
        return ReturnInfo.createReturnSuccessOne(cnarea2016s);
    }
}
