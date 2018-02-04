package com.dikong.lightcontroller.service.api;

import org.springframework.web.bind.annotation.RequestBody;

import com.dikong.lightcontroller.dto.DeviceApi;
import com.dikong.lightcontroller.dto.SendCmdRes;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * <p>
 * Description 对接 collection 服务的
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月30日下午5:56
 * @see
 *      </P>
 */
@Headers({"Content-Type: application/json"})
public interface DtuCollectionApi {

    @RequestLine("POST /device/create")
    SendCmdRes createDevice(@RequestBody DeviceApi deviceApi);

    @RequestLine("POST /device/modify")
    SendCmdRes modifyDevice(@RequestBody DeviceApi deviceApi);


    @RequestLine("DELETE /device/{registerMsg}")
    SendCmdRes deleteDevice(@Param(value = "registerMsg") String registerMsg);
}
