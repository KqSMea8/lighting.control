package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dto.DtuList;
import com.dikong.lightcontroller.entity.Dtu;

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
public interface DtuService {

    ReturnInfo list(DtuList dtuList);

    ReturnInfo deleteDtu(Long id);

    ReturnInfo addDtu(Dtu dtu);
}
