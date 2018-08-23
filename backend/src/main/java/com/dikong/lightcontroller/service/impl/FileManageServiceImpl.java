package com.dikong.lightcontroller.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.dao.FileManageDao;
import com.dikong.lightcontroller.entity.FileManage;
import com.dikong.lightcontroller.service.FileManageService;

/**
 * @author huangwenjun
 * @version 2018年8月21日 下午10:15:15
 */
@Service
public class FileManageServiceImpl implements FileManageService {

    @Value("${file.save.base.path}")
    private String fileBasePath;

    @Autowired
    private FileManageDao fileManageDao;

    private final static String SEPARATOR = File.separator;

    @Override
    @Transactional
    public void fileAdd(MultipartFile sourcefile, Integer fileType) throws Exception {

        String fileName = sourcefile.getOriginalFilename();
        String filePath = fileBasePath + SEPARATOR + fileType;
        File targetfile = new File(filePath + SEPARATOR + fileName);
        if (targetfile.exists()) {
            return;
        }
        // 先入库，再存文件
        FileManage fileManage = new FileManage();
        fileManage.setFileType(fileType);
        fileManage.setProjectId(1);
        fileManage.setFilePath(filePath + SEPARATOR + fileName);
        fileManage.setCreateBy(1);
        fileManageDao.insertSelective(fileManage);
        if (!targetfile.getParentFile().exists()) {
            targetfile.getParentFile().mkdirs();
        }
        targetfile.createNewFile();
        InputStream inputStream = sourcefile.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(targetfile);
        int byteCount = 0;
        byte[] bytes = new byte[1024];
        while ((byteCount = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, byteCount);
        }
        inputStream.close();
        outputStream.close();
        System.out.println("fileBasePath:" + fileBasePath);
    }

    @Override
    public void fileList(int projectId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void fileDel(int projectId) {
        // TODO Auto-generated method stub

    }

}
