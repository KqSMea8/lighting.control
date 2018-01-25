package com.dikong.lightcontroller.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.BussinessCode;
import com.dikong.lightcontroller.common.CodeEnum;
import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.DtuDAO;
import com.dikong.lightcontroller.dao.GroupDAO;
import com.dikong.lightcontroller.dao.GroupDeviceMiddleDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.service.DeviceService;
import com.dikong.lightcontroller.service.RegisterService;
import com.dikong.lightcontroller.utils.FileUtils;
import com.dikong.lightcontroller.vo.DeviceAdd;
import com.dikong.lightcontroller.vo.DeviceBoardList;
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

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private GroupDeviceMiddleDAO groupDeviceMiddleDAO;

    @Autowired
    private Environment environment;

    @Autowired
    private RegisterService registerService;

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
                deviceLists.add(device);
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
        String filePath = environment.getProperty("file.path");
        if (StringUtils.isEmpty(filePath)) {
            LOG.error("dot config file.path property");
            return ReturnInfo.create(CodeEnum.SERVER_ERROR);
        }

        String fileName = multipartFile.getOriginalFilename();
        int projId = 0;
        List<Register> registers = new LinkedList<>();
        try {
            fileName = id + "_" + fileName;
            byte[] content = multipartFile.getBytes();
            FileUtils.uploadFile(content, filePath, fileName);
            InputStream inputStream = multipartFile.getInputStream();
            List<String> multiLine = FileUtils.readFileByLine(inputStream);
            for (String item : multiLine) {
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
            }
            Integer conntionStatus = deviceDAO.selectConntionStatus(id);
            Register defaultRegister = null;
            if (null == conntionStatus || conntionStatus == 0) {
                defaultRegister = Register.addDefault(String.valueOf(Device.OFFLINE));
            } else {
                defaultRegister = Register.addDefault(String.valueOf(Device.ONLINE));
            }
            registers.add(defaultRegister);
        } catch (IOException e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
            return ReturnInfo.create(CodeEnum.REQUEST_PARAM_ERROR);
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
            return ReturnInfo.create(CodeEnum.DATA_INVALID);
        }
        String ifExistModeFile = deviceDAO.selectModeFile(id);
        if (!StringUtils.isEmpty(ifExistModeFile)) {
            // 如果存在就更新之前的变量,不能直接删除
            Map<String, String> existRegister = new HashMap<>();
            List<String> regisAddrs = registerDAO.selectRegisAddrByDeviceId(id);
            if (!CollectionUtils.isEmpty(regisAddrs)) {
                regisAddrs.forEach(item -> existRegister.put(item, item));
            }
            Iterator<Register> iterator = registers.iterator();
            while (iterator.hasNext()) {
                Register register = iterator.next();
                String exist = existRegister.get(register.getRegisAddr());
                if (null != exist) {
                    registerService.updateRegister(register);
                    iterator.remove();
                }
            }
        }
        registerDAO.insertMultiItem(registers);
        String modilFilePath = filePath + fileName;
        deviceDAO.updateModeFilePathById(id, modilFilePath);
        return ReturnInfo.create(CodeEnum.SUCCESS);
    }


    @Override
    public ReturnInfo idList(Long dtuId) {
        List<Device> devices = deviceDAO.selectIdList(dtuId, Device.DEL_NO);
        if (!CollectionUtils.isEmpty(devices)) {
            devices.forEach(item -> {
                String code = "ID" + Integer.parseInt(item.getCode());
                item.setCode(code);
            });
        }
        return ReturnInfo.createReturnSuccessOne(devices);
    }


    @Override
    public ReturnInfo selectAllSelectDevice() {
        int projId = 0;
        List<DeviceBoardList> deviceBoardLists = deviceDAO.selectNotIn(projId);
        if (!CollectionUtils.isEmpty(deviceBoardLists)) {
            deviceBoardLists.forEach(item -> {
                String deviceLocaltion =
                        item.getDtuName() + ":ID" + Integer.parseInt(item.getDeviceCode());
                item.setDeviceLocation(deviceLocaltion);
            });
        }
        return ReturnInfo.createReturnSuccessOne(deviceBoardLists);
    }
}
