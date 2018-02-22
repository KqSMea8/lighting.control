package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.ProjectDao;
import com.dikong.lightcontroller.dao.UserProjectDao;
import com.dikong.lightcontroller.dto.ProjectListReq;
import com.dikong.lightcontroller.entity.Project;
import com.dikong.lightcontroller.entity.UserProject;
import com.dikong.lightcontroller.service.ProjectService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午9:32:00
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserProjectDao userProjectDao;

    @Override
    public ReturnInfo<List<Project>> projectList(ProjectListReq projectListReq) {
        PageHelper.startPage(projectListReq.getPageNo(), projectListReq.getPageSize());
        Example example = new Example(UserProject.class);
        example.selectProperties("projectId");
        example.createCriteria().andEqualTo("userId", AuthCurrentUser.getUserId());
        List<UserProject> userProjects = userProjectDao.selectByExample(example);
        List<Integer> projectIds = new ArrayList<>();
        for (UserProject userProject : userProjects) {
            projectIds.add(userProject.getProjectId());
        }
        if (projectIds.size() == 0) {
            return ReturnInfo.create(CodeEnum.NOT_CONTENT);
        }
        List<Project> projects = projectDao.projectList(projectIds);
        return ReturnInfo.createReturnSuccessOne(projects);
    }

    @Override
    public ReturnInfo projectAdd(Project project) {
        project.setCreateBy(AuthCurrentUser.getUserId());
        projectDao.insertSelective(project);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    @Transactional
    public ReturnInfo projectRemove(int projectId) {
        Project project = new Project();
        project.setProjectId(projectId);;
        project.setIsDelete(2);
        project.setUpdateBy(AuthCurrentUser.getUserId());
        projectDao.updateByPrimaryKeySelective(project);
        Example example = new Example(UserProject.class);
        example.createCriteria().andEqualTo("projectId", projectId);
        userProjectDao.deleteByExample(example);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo projectUpdate(Project project) {
        project.setUpdateBy(AuthCurrentUser.getUserId());
        projectDao.updateByPrimaryKeySelective(project);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

}
