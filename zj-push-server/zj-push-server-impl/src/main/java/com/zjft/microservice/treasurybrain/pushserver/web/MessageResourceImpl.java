package com.zjft.microservice.treasurybrain.pushserver.web;

import com.zjft.microservice.treasurybrain.pushserver.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/2/21
 */
@RestController
@Slf4j
public class MessageResourceImpl implements MessageResource {

	@Resource
	private MessageService messageService;

	@Override
	public Map<String, Object> sendSmsInfo(String signName, String phoneNumber, String message) {
		return messageService.sendSmsInfo(signName,phoneNumber,message);
	}

	@Override
	public Map<String, Object> sendBatchSmsInfo(String signName, String batchPhoneNumber, String message) {
		return messageService.sendBatchSmsInfo(signName,batchPhoneNumber,message);
	}
}
