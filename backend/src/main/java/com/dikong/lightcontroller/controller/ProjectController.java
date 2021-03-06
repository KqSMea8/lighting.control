package com.dikong.lightcontroller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.Project;
import com.dikong.lightcontroller.service.ProjectService;

@RestController
@RequestMapping("/light/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping("/list")
    public ReturnInfo list() {
        return projectService.projectList();
    }

    @PostMapping("/add")
    public ReturnInfo add(@RequestBody Project project) {
        return projectService.projectAdd(project);
    }

    @GetMapping("/del/{projectId}")
    public ReturnInfo del(@PathVariable("projectId") int projectId) {
        return projectService.projectRemove(projectId);
    }

    @PostMapping("/update")
    public ReturnInfo update(@RequestBody Project project) {
        return projectService.projectUpdate(project);
    }
}
