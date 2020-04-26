package com.zjft.microservice.treasurybrain.pushserver.service.impl;

import com.zjft.microservice.treasurybrain.pushserver.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 常 健
 * @since 2020/2/21
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {


	@Override
	public Map<String, Object> sendSmsInfo(String signName, String phoneNumber, String message) {
		Map<String, Object> map = new HashMap<>();
		Random rand = new Random();
		int randNum = rand.nextInt(5);
		if (randNum == 0 || randNum == 1 || randNum == 2) {
			log.info("【" + signName + "】");
			log.info("接收人号码：" + phoneNumber);
			log.info("发送内容：" + message);
			map.put("retCode", "ok");
			map.put("requestId", java.util.UUID.randomUUID().toString().replace("-", ""));
			return map;
		}
		map.put("retCode", "fail");
		map.put("requestId", java.util.UUID.randomUUID().toString().replace("-", ""));
		return map;
	}

	@Override
	public Map<String, Object> sendBatchSmsInfo(String signName, String batchPhoneNumber, String message) {
		Map<String, Object> map = new HashMap<>();
		Random rand = new Random();
		int randNum = rand.nextInt(5);
		if (randNum == 0 || randNum == 1 || randNum == 2) {
			log.info("【" + signName + "】");
			log.info("接收人号码：" + batchPhoneNumber);
			log.info("发送内容：" + message);
			map.put("retCode", "ok");
			map.put("requestId", java.util.UUID.randomUUID().toString().replace("-", ""));
			return map;
		}
		map.put("retCode", "fail");
		map.put("requestId", java.util.UUID.randomUUID().toString().replace("-", ""));
		return map;
	}
}
