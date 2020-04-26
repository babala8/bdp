package com.zjft.microservice.treasurybrain.pushserver.web;

import io.swagger.annotations.Api;

import java.util.Map;

/**
 * @author 常 健
 * @since 2020/2/21
 */
@Api(value = "推送中心：短信发送调用接口", tags = "推送中心：短信发送调用接口")
public interface MessageResource {

	String PREFIX = "/push-server/v2/message";

	/**
	 * 短信发送
	 *
	 * @param signName,phoneNumber,message 字段根据第三方接口而定（测试：String signName, String phoneNumber, String message）
	 */
	Map<String, Object> sendSmsInfo(String signName, String phoneNumber, String message);


	/**
	 * 短信发送（群发）
	 *
	 * @param signName,phoneNumber,message 字段根据第三方接口而定（测试：String signName, String batchPhoneNumber, String message）
	 */
	Map<String, Object> sendBatchSmsInfo(String signName, String batchPhoneNumber, String message);

}
