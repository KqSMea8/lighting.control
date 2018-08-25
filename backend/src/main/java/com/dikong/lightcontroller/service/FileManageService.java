package com.dikong.lightcontroller.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.entity.FileManage;

/**
 * @author huangwenjun
 * @version 2018年8月21日 上午12:46:55
 */
public interface FileManageService {

    public ReturnInfo fileAdd(MultipartFile file, Integer fileType) throws Exception;

    public ReturnInfo<List<FileManage>> fileList(int fileType);

    public void findInfo(HttpServletResponse response, int fileId) throws Exception;

    public void fileDel(int projectId);

}
