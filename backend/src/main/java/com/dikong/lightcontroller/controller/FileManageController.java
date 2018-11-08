package com.dikong.lightcontroller.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.FileManage;
import com.dikong.lightcontroller.service.FileManageService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author huangwenjun
 * @version 2018年8月21日 下午10:56:00
 */
@RestController
@RequestMapping("/light/file")
public class FileManageController {

    @Autowired
    private FileManageService fileManageService;

    @PostMapping("/upload/{type}")
    @ApiOperation("文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "文件类型 1->图片 2->音频 3->点位文件", required = true,
                    dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "file", value = "文件内容", required = true, dataType = "file",
                    paramType = "file")})
    public ReturnInfo upload(@RequestParam("file") MultipartFile file,
            @PathVariable("type") Integer fileType) throws Exception {
        return fileManageService.fileAdd(file, fileType);
    }

    @GetMapping("/list/{type}")
    @ApiOperation("文件列表")
    @ApiImplicitParam(name = "type", value = "文件类型 1->图片 2->音频 3->点位文件", required = true,
            dataType = "Integer", paramType = "path")
    public ReturnInfo<List<FileManage>> fileList(@PathVariable("type") Integer fileType)
            throws Exception {
        return fileManageService.fileList(fileType);
    }

    @GetMapping("/one/{id}")
    @ApiOperation("根据id获取单个文件")
    @ApiImplicitParam(name = "id", value = "文件id", required = true, dataType = "Integer",
            paramType = "path")
    public void fileInfo(HttpServletResponse response, @PathVariable("id") Integer fileId)
            throws Exception {
        fileManageService.findInfo(response, fileId);
    }
}
