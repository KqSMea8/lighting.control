package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.DtuList;
import com.dikong.lightcontroller.entity.Dtu;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午2:25
 * @see
 *      </P>
 */
public interface DtuService {

    ReturnInfo<List<Dtu>> list(DtuList dtuList);

    ReturnInfo<List<Dtu>> dtuListNoPage(DtuList dtuList);

    ReturnInfo deleteDtu(Long id);

    ReturnInfo deleteAllDtu();

    ReturnInfo addDtu(Dtu dtu);

    ReturnInfo<List<Dtu>> idList();

    ReturnInfo updateDtu(Dtu dtu);

    ReturnInfo conncationInfo(String deviceCode,Integer line);

    ReturnInfo<List<Dtu>> allDtu();
}
