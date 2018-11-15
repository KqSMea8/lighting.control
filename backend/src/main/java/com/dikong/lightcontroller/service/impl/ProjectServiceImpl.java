package com.dikong.lightcontroller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.ProjectDao;
import com.dikong.lightcontroller.dao.UserProjectDao;
import com.dikong.lightcontroller.dto.ProjectListReq;
import com.dikong.lightcontroller.dto.QuartzJobDto;
import com.dikong.lightcontroller.entity.Project;
import com.dikong.lightcontroller.entity.UserProject;
import com.dikong.lightcontroller.service.DtuService;
import com.dikong.lightcontroller.service.ProjectService;
import com.dikong.lightcontroller.service.TaskService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;
import com.dikong.lightcontroller.utils.JedisProxy;
import com.github.pagehelper.PageHelper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import tk.mybatis.mapper.entity.Example;

/**
 * @author huangwenjun
 * @version 2018年1月20日 下午9:32:00
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);
    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserProjectDao userProjectDao;


    @Autowired
    private DtuService dtuService;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private TaskService taskService;

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
        AuthCurrentUser.setCurrentProjectId(projectId);
        Project project = new Project();
        project.setProjectId(projectId);;
        project.setIsDelete(2);
        project.setUpdateBy(AuthCurrentUser.getUserId());
        projectDao.updateByPrimaryKeySelective(project);
        Example example = new Example(UserProject.class);
        example.createCriteria().andEqualTo("projectId", projectId);
        userProjectDao.deleteByExample(example);
        dtuService.deleteAllDtu();
        // 删除之前存储的重发命令
        Jedis jedis = new JedisProxy(jedisPool).createProxy();
        jedis.del(Constant.RESERT_CMD.KEY_PROFILE + String.valueOf(projectId));
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo projectUpdate(Project project) {
        Project oldProject = projectDao.selectByPrimaryKey(project.getProjectId());
        if (!oldProject.getRefreshInterval().equals(project.getRefreshInterval())) {
            // 定时任务刷新时间改变
            taskService.removeGraphTask(oldProject.getTaskName());
            String cronStr = "0 0/" + project.getRefreshInterval() + " * * * ? ";
            ReturnInfo returnInfo = taskService.addGraphTask(project.getProjectId(), cronStr);
            if (null != returnInfo.getData()) {
                QuartzJobDto quartzJobDto = (QuartzJobDto) returnInfo.getData();
                project.setTaskName(quartzJobDto.getJobDO().getName());
                projectDao.updateByPrimaryKey(project);
                LOG.info("定时任务添加成功：项目id:" + project.getProjectId() + " job name="
                        + quartzJobDto.getJobDO().getName());
            } else {
                return ReturnInfo.create(CodeEnum.SERVER_ERROR.getCode(),
                        CodeEnum.SERVER_ERROR + ":定时任务创建失败，请稍后重试！");
            }
        }
        project.setUpdateBy(AuthCurrentUser.getUserId());
        projectDao.updateByPrimaryKeySelective(project);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo<List<Project>> findAllEnableProj() {
        return ReturnInfo.createReturnSuccessOne(projectDao.selectAll());
    }

}
