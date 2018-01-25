package com.dikong.lightcontroller.service;

import org.springframework.web.multipart.MultipartFile;

import com.dikong.lightcontroller.common.ReturnInfo;
import com.dikong.lightcontroller.vo.DeviceAdd;

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

    ReturnInfo list(Long dtuId);

    ReturnInfo deleteDevice(Long id);

    ReturnInfo addDevice(DeviceAdd deviceAdd);

    ReturnInfo uploadPointTableFile(MultipartFile multipartFile, Long id);

    ReturnInfo idList(Long dtuId);

    ReturnInfo selectAllSelectDevice();
}
