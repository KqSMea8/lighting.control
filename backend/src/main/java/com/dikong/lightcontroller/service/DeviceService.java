package com.dikong.lightcontroller.service;

import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.entity.Dtu;
import com.dikong.lightcontroller.vo.DeviceBoardList;
import com.dikong.lightcontroller.vo.DeviceList;
import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.vo.DeviceAdd;

import java.util.List;

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

public interface DeviceService {

    ReturnInfo<List<DeviceList>> list(Long dtuId);

    ReturnInfo deleteDevice(Long id);

    ReturnInfo addDevice(DeviceAdd deviceAdd);

    ReturnInfo uploadPointTableFile(MultipartFile multipartFile, Long id);

    ReturnInfo<List<Device>> idList(Long dtuId);

    ReturnInfo<List<DeviceBoardList>> selectAllSelectDevice();

    ReturnInfo conncationInfo(Long id);

}
