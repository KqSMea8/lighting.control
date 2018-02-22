package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Cnarea2016;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月15日上午8:20
 * @see </P>
 */
public interface CnareaService {

    ReturnInfo<List<Cnarea2016>> allCity(Long parentId);
}
