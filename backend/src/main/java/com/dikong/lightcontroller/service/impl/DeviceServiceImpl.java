package com.dikong.lightcontroller.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.BussinessCode;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.DtuDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.service.DeviceService;
import com.dikong.lightcontroller.utils.FileUtils;
import com.dikong.lightcontroller.vo.DeviceAdd;
import com.dikong.lightcontroller.vo.DeviceList;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月20日下午1:49
 * @see
 *      </P>
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private DtuDAO dtuDAO;

    @Autowired
    private RegisterDAO registerDAO;


    /**
     * 只有16个不需要分页
     * 
     * @param dtuId
     * @return
     */
    @Override
    public ReturnInfo list(Long dtuId) {
        List<Device> deviceList = deviceDAO.selectAllByDtuId(dtuId, Device.DEL_NO);
        List<DeviceList> deviceLists = new ArrayList<>();
        if (!CollectionUtils.isEmpty(deviceList)) {
            String dtuName = dtuDAO.selectAllNameById(dtuId);
            deviceList.forEach(item -> {
                DeviceList device = new DeviceList();
                BeanUtils.copyProperties(item, device);
                device.setDtuName(dtuName);
            });
        }
        return ReturnInfo.createReturnSuccessOne(deviceLists);
    }

    @Override
    public ReturnInfo deleteDevice(Long id) {
        deviceDAO.updateDeleteById(id, Device.DEL_YES);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo addDevice(DeviceAdd deviceAdd) {
        int existNum = deviceDAO.selectByDtuIdAndCode(deviceAdd.getDtuId(), deviceAdd.getCode());
        if (existNum > 0) {
            return ReturnInfo.create(BussinessCode.DEVICE_EXIST.getCode(),
                    BussinessCode.DEVICE_EXIST.getMsg());
        }
        deviceDAO.insertDevice(deviceAdd);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo uploadPointTableFile(MultipartFile multipartFile, Long id) {
        String filePath = System.getProperty("file.path");
        if (StringUtils.isEmpty(filePath)) {
            LOG.error("dot config file.path property");
            return ReturnInfo.create(CodeEnum.SERVER_ERROR);
        }
        String fileName = multipartFile.getOriginalFilename();
        int projId = 0;
        try {
            fileName = id + "_" + fileName;
            byte[] content = multipartFile.getBytes();
            FileUtils.uploadFile(content, filePath, fileName);
            InputStream inputStream = multipartFile.getInputStream();
            List<String> multiLine = FileUtils.readFileByLine(inputStream);
            List<Register> registers = new ArrayList<>();
            multiLine.forEach(item -> {
                Register register = new Register();
                String[] regisName = item.split("=");
                register.setRegisName(regisName[0]);
                String[] regisAddrs = regisName[1].split(",");
                register.setRegisAddr(regisAddrs[0]);
                String[] regisType = regisAddrs[1].split(";");
                register.setRegisType(regisType[0]);
                register.setVarName(regisType[1]);
                register.setDeviceId(id);
                register.setProjId(projId);
                registers.add(register);
            });
            registerDAO.insertMultiItem(registers);


            String modilFilePath = filePath + fileName;
            deviceDAO.updateModeFilePathById(id, modilFilePath);
        } catch (IOException e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
            return ReturnInfo.create(CodeEnum.DATA_INVALID);
        }

        return ReturnInfo.create(CodeEnum.SUCCESS);
    }
}
