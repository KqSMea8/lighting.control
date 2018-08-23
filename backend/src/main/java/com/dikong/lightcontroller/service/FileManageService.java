package com.dikong.lightcontroller.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author huangwenjun
 * @version 2018年8月21日 上午12:46:55
 */
public interface FileManageService {

    public void fileAdd(MultipartFile file, Integer fileType) throws Exception;

    public void fileList(int projectId);

    public void fileDel(int projectId);

}
