package com.dikong.lightcontroller.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.FileManageDao;
import com.dikong.lightcontroller.entity.FileManage;
import com.dikong.lightcontroller.service.FileManageService;
import com.dikong.lightcontroller.utils.AuthCurrentUser;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author huangwenjun
 * @version 2018年8月21日 下午10:15:15
 */
@Service
public class FileManageServiceImpl implements FileManageService {

    @Value("${file.save.base.path}")
    private String fileBasePath;

    @Value("${imgs.save.base.path}")
    private String imgBasePath;

    @Autowired
    private FileManageDao fileManageDao;

    private final static String SEPARATOR = File.separator;

    @Override
    @Transactional
    public ReturnInfo fileAdd(MultipartFile sourcefile, Integer fileType) throws Exception {
        String fileName = sourcefile.getOriginalFilename();
        String savePath = SEPARATOR + AuthCurrentUser.getCurrentProjectId() + SEPARATOR + fileType;
        String filePath = fileBasePath + savePath;
        File targetfile = new File(filePath + SEPARATOR + fileName);
        if (targetfile.exists()) {
            return ReturnInfo.create(CodeEnum.FILE_EXIST);
        }
        // 先入库，再存文件
        FileManage fileManage = new FileManage();
        fileManage.setFileType(fileType);
        fileManage.setProjectId(AuthCurrentUser.getCurrentProjectId());
        fileManage.setFilePath(savePath + SEPARATOR + fileName);
        fileManage.setFileName(fileName);
        fileManage.setCreateBy(AuthCurrentUser.getUserId());
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
        return ReturnInfo.createReturnSuccessOne(null);
    }

    @Override
    public ReturnInfo<List<FileManage>> fileList(int fileType) {
        Example example = new Example(FileManage.class);
        Criteria criteria = example.createCriteria();
        if (!AuthCurrentUser.isManager()) {
            criteria.andEqualTo("projectId", AuthCurrentUser.getCurrentProjectId());
        }
        if (fileType != 0) {
            criteria.andEqualTo("fileType", fileType);
        }
        List<FileManage> result = fileManageDao.selectByExample(example);
        return ReturnInfo.createReturnSuccessOne(result);
    }

    @Override
    public void findInfo(HttpServletResponse response, int fileId) throws Exception {
        FileManage fileManage = fileManageDao.selectByPrimaryKey(fileId);
        if ((fileManage == null
                || fileManage.getProjectId() != AuthCurrentUser.getCurrentProjectId())) {
            if (!AuthCurrentUser.isManager()) {
                return;
            }
        }
        File file = new File(fileManage.getFilePath());
        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            int byteCount = 0;
            byte[] bytes = new byte[1024];
            while ((byteCount = inputStream.read(bytes)) != -1) {
                response.getOutputStream().write(bytes, 0, byteCount);
            }
            inputStream.close();
        }
    }

    @Override
    public void fileDel(int projectId) {
        // TODO Auto-generated method stub

    }
}
