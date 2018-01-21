package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Project;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午9:23:43
 */
public interface ProjectService {
    public ReturnInfo projectList();

    public ReturnInfo projectAdd(Project project);

    public ReturnInfo projectRemove(int projectId);

    public ReturnInfo projectUpdate(Project project);
}
