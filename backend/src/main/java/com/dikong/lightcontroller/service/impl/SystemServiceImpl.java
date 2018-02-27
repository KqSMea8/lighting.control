package com.dikong.lightcontroller.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.BussinessCode;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.SystemDAO;
import com.dikong.lightcontroller.entity.SystemConfig;
import com.dikong.lightcontroller.service.SystemService;
import com.dikong.lightcontroller.utils.FileUtils;
import com.dikong.lightcontroller.vo.SystemSearch;
import com.github.pagehelper.PageHelper;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年02月24日上午11:00
 * @see
 *      </P>
 */
@Service
public class SystemServiceImpl implements SystemService {

    private static final Logger LOG = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private SystemDAO systemDAO;

    @Autowired
    private Environment environment;

    /**
     *
     * @param typeValue
     * @return
     */
    @Override
    public ReturnInfo addTypeValue(String typeValue) {
        int typeIsExist = systemDAO.selectTypeIsExist(typeValue);
        if (typeIsExist > 0) {
            return ReturnInfo.create(BussinessCode.SYSTEM_TYPE_EXIST.getCode(),
                    BussinessCode.SYSTEM_TYPE_EXIST.getMsg());
        }
        Integer lastTypeId = systemDAO.selectLastTypeId();
        if (null == lastTypeId) {
            lastTypeId = 1;
        } else {
            lastTypeId += 1;
        }
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setTypeId(lastTypeId);
        systemConfig.setTypeValue(typeValue);
        systemDAO.insertSystem(systemConfig);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }

    @Override
    public ReturnInfo<List<SystemConfig>> listType() {
        SystemSearch systemSearch = new SystemSearch();
        List<SystemConfig> systemConfigs = systemDAO.selectAll(systemSearch);
        return ReturnInfo.createReturnSucces(systemConfigs);
    }

    @Override
    public ReturnInfo add(SystemConfig systemConfig, MultipartFile uploadfile) throws IOException {
        if (null == uploadfile || uploadfile.isEmpty()) {
            systemConfig.setValueType(SystemConfig.CHAR);
        } else {
            String filePath = environment.getProperty("system.file.path");
            if (StringUtils.isEmpty(filePath)) {
                LOG.error("dot no config file.path property");
                return ReturnInfo.create(CodeEnum.SERVER_ERROR);
            }
            systemConfig.setValueType(SystemConfig.FILE);
            String fileName = uploadfile.getOriginalFilename();
            byte[] content = uploadfile.getBytes();
            FileUtils.uploadFile(content, filePath, fileName);
            systemConfig.setValue(fileName);
        }
        systemConfig.setTypeId(systemConfig.getTypeId());
        systemDAO.updateSystemByTypeId(systemConfig);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo<SystemConfig> search(Integer valueId) {
        SystemConfig systemConfig = systemDAO.selectByTypeId(valueId);
        return ReturnInfo.create(systemConfig);
    }

    @Override
    public ReturnInfo<List<SystemConfig>> list(SystemSearch systemSearch) {
        PageHelper.startPage(systemSearch.getPageNo(), systemSearch.getPageSize());
        List<SystemConfig> systemConfigs = systemDAO.selectAll(systemSearch);
        return ReturnInfo.createReturnSucces(systemConfigs);
    }

    @Override
    public ReturnInfo del(Long id) {
        SystemConfig systemConfig = systemDAO.selectById(id);
        if (null == systemConfig) {
            return ReturnInfo.create(CodeEnum.SUCCESS);
        }
        if (SystemConfig.FILE.equals(systemConfig.getValueType())) {
            String filePath = environment.getProperty("system.file.path");
            File f = new File(filePath + systemConfig.getValue());
            if (f.exists() && f.isFile()) {
                f.delete();
            }
        }
        systemDAO.deleteById(id);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }
}
