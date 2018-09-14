package com.dikong.lightcontroller.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dikong.lightcontroller.entity.History;
import com.dikong.lightcontroller.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.dikong.lightcontroller.common.Constant;
import com.dikong.lightcontroller.dao.DeviceDAO;
import com.dikong.lightcontroller.dao.RegisterDAO;
import com.dikong.lightcontroller.dto.CmdRes;
import com.dikong.lightcontroller.entity.Device;
import com.dikong.lightcontroller.entity.Register;
import com.dikong.lightcontroller.service.CmdService;
import com.dikong.lightcontroller.utils.JedisProxy;
import com.dikong.lightcontroller.utils.RTCUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RTCSendTask {

	private static final Logger LOG = LoggerFactory.getLogger(RTCSendTask.class);

	@Autowired
	private DeviceDAO deviceDAO;

	@Autowired
	private RegisterDAO registerDAO;

	@Autowired
	private CmdService cmdService;

	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private HistoryService historyService;




	@Scheduled(cron = "30 0 04 * * *")
	public void sendRtcTimeTask04(){
		long start = System.currentTimeMillis();
		LOG.info("凌晨4点开始下发RTC:{}",new Date());
		List<Device> devices = deviceDAO.selectAllByIsDelete(Device.DEL_NO);
		String[] registerAddrs = {"40028","40029"};
		Long[] deviceRTC = RTCUtils.DeviceRTC();
		send(devices,registerAddrs,deviceRTC);
		LOG.info("凌晨4点下发结束:{},总共耗时:{} ms",new Date(),System.currentTimeMillis()-start);
	}

	@Scheduled(cron = "30 0 16 * * *")
	public void sendRtcTimeTask16(){
		long start = System.currentTimeMillis();
		LOG.info("16点开始下发RTC:{}",new Date());
		List<Device> devices = deviceDAO.selectAllByIsDelete(Device.DEL_NO);
		String[] registerAddrs = {"40028","40029"};
		Long[] deviceRTC = RTCUtils.DeviceRTC();
		send(devices,registerAddrs,deviceRTC);
		LOG.info("16点下发结束:{},总共耗时:{} ms",new Date(),System.currentTimeMillis()-start);
	}

	@Scheduled(cron = "40 */7 * * * *")
	public void resertSendRTC(){
		String[] registerAddrs = {"40028","40029"};
		Jedis jedis = new JedisProxy(jedisPool).createProxy();
		Map<String, String> failDevice = jedis.hgetAll(Constant.RTC.RESERT_KEY);
		LOG.info("开始重新下发RTC时钟:{},重发条数:{}",new Date(),failDevice != null ? failDevice.size():0 );
		if (!CollectionUtils.isEmpty(failDevice)){
			for (String deviceId : failDevice.keySet()) {
				Device device = deviceDAO.selectDeviceById(Long.parseLong(deviceId));
				Long[] deviceRTC = RTCUtils.DeviceRTC();
				this.send(Arrays.asList(device),registerAddrs,deviceRTC);
			}
		}
	}

	public void send(List<Device> devices,String[] registerAddrs,Long[] deviceRTC){

		Jedis jedis = new JedisProxy(jedisPool).createProxy();
		if (!CollectionUtils.isEmpty(devices)){
			for (Device device : devices) {
				List<Register> registers =
						registerDAO.selectRegisterByDeviceId(device.getId(), registerAddrs);
				if (!CollectionUtils.isEmpty(registers) && registers.size() == 2){
					CmdRes<String> cmdRes = cmdService
							.writeAnalog(registers.get(0).getId(), deviceRTC[0].intValue());
					if (!cmdRes.isSuccess()){
						//如果第一个没成功，就不再下发
						jedis.hdel(Constant.RESERT_CMD.KEY_PROFILE + registers.get(0).getProjId(),
								String.valueOf(registers.get(0).getId()));
						jedis.hset(Constant.RTC.RESERT_KEY,String.valueOf(device.getId()),String.valueOf(device.getId()));
						continue;
					}
					cmdRes = cmdService.writeAnalog(registers.get(1).getId(),deviceRTC[1].intValue());
					if (!cmdRes.isSuccess()){
						//如果第二次没成功，就删除第二次的重试记录
						jedis.hdel(Constant.RESERT_CMD.KEY_PROFILE + registers.get(1).getProjId(),
								String.valueOf(registers.get(1).getId()));
						jedis.hset(Constant.RTC.RESERT_KEY,String.valueOf(device.getId()),String.valueOf(device.getId()));
						continue;
					}
					//如果都发送成功了，就删除之前存储失败的
					jedis.hdel(Constant.RTC.RESERT_KEY,String.valueOf(device.getId()));
					//保存更新记录
					List<History> histories = new ArrayList<>();
					histories.add(new History(registers.get(0).getId(),History.REGISTER_TYPE,String.valueOf(deviceRTC[0].intValue())));
					histories.add(new History(registers.get(1).getId(),History.REGISTER_TYPE,String.valueOf(deviceRTC[1].intValue())));
					historyService.updateHistory(histories);

				}
			}
		}
	}
}
