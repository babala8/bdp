package com.zjft.microservice.treasurybrain.pushserver.service;

import java.util.Map;

/**
 * @author 常 健
 * @since 2020/2/21
 */
public interface MessageService {

	Map<String,Object> sendSmsInfo(String signName, String phoneNumber, String message);

	Map<String,Object> sendBatchSmsInfo(String signName, String batchPhoneNumber, String message);
}
