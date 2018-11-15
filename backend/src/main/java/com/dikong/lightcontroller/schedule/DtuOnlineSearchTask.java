package com.dikong.lightcontroller.schedule;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.dikong.lightcontroller.service.DtuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dikong.lightcontroller.dto.CollectionDeviceAll;
import com.dikong.lightcontroller.utils.OkhttpUtils;

/**
 * 定时查询dtu的在线情况
 */
@Component
public class DtuOnlineSearchTask {


	private static final String serviceIpKey = "collection.host";

	private static Logger LOG = LoggerFactory.getLogger(DtuOnlineSearchTask.class);

	@Autowired
	private Environment envioroment;

	@Autowired
	private DtuService dtuService;

	@Scheduled(cron = "0 */10 * * * *")
	public void scanTimingNewTask() throws IOException {
		String url = envioroment.getProperty(serviceIpKey) + "/device/all";
		String resp = OkhttpUtils.get(url);
		CollectionDeviceAll collectionDeviceAll = JSON.parseObject(resp, CollectionDeviceAll.class);
		if (collectionDeviceAll.getData() != null) {
			LOG.info("定时调用dtu在线列表返回:{},时间为:{}",collectionDeviceAll.getData().size(),new Date().toString());
			for (Map.Entry<String, CollectionDeviceAll.DtuStatus> stringDtuStatusEntry : collectionDeviceAll
					.getData().entrySet()) {
				if (stringDtuStatusEntry != null && stringDtuStatusEntry.getKey() != null
						&& stringDtuStatusEntry.getValue() != null
						&& stringDtuStatusEntry.getValue().getStatus() != null) {
					dtuService.conncationInfo(stringDtuStatusEntry.getKey(),stringDtuStatusEntry.getValue().getStatus());
				}
			}
		}
	}
}
