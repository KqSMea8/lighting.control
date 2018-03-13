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
import com.dikong.lightcontroller.service.DtuService;
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


    @Autowired
    private DtuService dtuService;

    @Override
    public ReturnInfo<List<Project>> projectList(ProjectListReq projectListReq) {

        if (AuthCurrentUser.isManager()) {
            Example example = new Example(Project.class);
            example.createCriteria().andEqualTo("isDelete", 1);
            PageHelper.startPage(projectListReq.getPageNo(), projectListReq.getPageSize());
            List<Project> projects = projectDao.selectByExample(example);
            return ReturnInfo.createReturnSucces(projects);
        }
        Example example = new Example(UserProject.class);
        example.selectProperties("projectId");
        example.createCriteria().andEqualTo("userId", AuthCurrentUser.getUserId());
        List<UserProject> userProjects = userProjectDao.selectByExample(example);
        List<Integer> projectIds = new ArrayList<>();
        for (UserProject userProject : userProjects) {
            projectIds.add(userProject.getProjectId());
        }
        PageHelper.startPage(projectListReq.getPageNo(), projectListReq.getPageSize());
        List<Project> projects = new ArrayList<Project>();
        if (projectIds.size() != 0) {
            // return ReturnInfo.create(CodeEnum.NOT_CONTENT);
            projects = projectDao.projectList(projectIds);
        }
        return ReturnInfo.createReturnSucces(projects);
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
        dtuService.deleteAllDtu();
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo projectUpdate(Project project) {
        project.setUpdateBy(AuthCurrentUser.getUserId());
        projectDao.updateByPrimaryKeySelective(project);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

}
