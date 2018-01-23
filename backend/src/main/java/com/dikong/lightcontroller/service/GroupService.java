package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Group;
import com.dikong.lightcontroller.vo.GroupList;

import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月22日下午3:28
 * @see </P>
 */
public interface GroupService {

    ReturnInfo list(GroupList groupList);

    ReturnInfo add(Group group);

    ReturnInfo deleteGroup(Long id);

    ReturnInfo deviceList(Long id);

    ReturnInfo deleteGroupDevice(List<Long> middId);
}
