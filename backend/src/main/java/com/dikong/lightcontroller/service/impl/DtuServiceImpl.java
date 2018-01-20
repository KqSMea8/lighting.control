package com.dikong.lightcontroller.service.impl;

import com.dikong.lightcontroller.dao.DtuDAO;
import com.dikong.lightcontroller.dto.DtuList;
import com.dikong.lightcontroller.entity.Dtu;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.service.DtuService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午2:25
 * @see </P>
 */
@Service
public class DtuServiceImpl implements DtuService {

    private static final Logger LOG = LoggerFactory.getLogger(DtuServiceImpl.class);

    @Autowired
    private DtuDAO dtuDAO;

    @Override
    public ReturnInfo list(DtuList dtuList) {
        PageHelper.startPage(dtuList.getPageNo(),dtuList.getPageSize());
        List<Dtu> dtus = dtuDAO.selectAllByPage();
        if (null  == dtus){
            dtus = new ArrayList<Dtu>();
        }
        return ReturnInfo.createReturnSucces(dtus);
    }

    @Override
    public ReturnInfo deleteDtu(Long id) {
        dtuDAO.updateIsDelete(id,Dtu.DEL_YES);
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo addDtu(Dtu dtu) {
        dtuDAO.insertDtu(dtu);
        return ReturnInfo.createReturnSuccessOne(null);
    }
}
