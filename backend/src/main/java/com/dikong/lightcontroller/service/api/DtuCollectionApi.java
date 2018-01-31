package com.dikong.lightcontroller.service.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.dikong.lightcontroller.dto.DeviceApi;

import feign.Headers;
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
    int createDevice(@RequestBody DeviceApi deviceApi);

    @RequestLine("POST /device/modify")
    int modifyDevice(@RequestBody DeviceApi deviceApi);


    @RequestLine("POST /device/{registerMsg}")
    int deleteDevice(@PathVariable("registerMsg") String registerMsg);
}
