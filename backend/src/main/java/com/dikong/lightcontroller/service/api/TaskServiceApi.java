package com.dikong.lightcontroller.service.api;

import org.springframework.web.bind.annotation.RequestBody;

import com.dikong.lightcontroller.dto.QuartzJobDto;

import feign.Headers;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月29日下午10:08
 * @see
 *      </P>
 */
@Headers({"Content-Type: application/json"})
public interface TaskServiceApi {

    @RequestLine("POST /jobs")
    boolean addTask(@RequestBody QuartzJobDto quartzJobDto);


    @RequestLine("DELETE /jobs")
    boolean delTask(@RequestBody Map<String,List<String>> jobKeyGroups);
}
