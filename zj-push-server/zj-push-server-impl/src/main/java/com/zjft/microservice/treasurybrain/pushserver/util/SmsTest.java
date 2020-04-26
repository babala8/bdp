package com.zjft.microservice.treasurybrain.pushserver.util;

/**
 * 短信发送模拟接口
 * @author 常 健
 * @since 2020/2/21
 */
public interface SmsTest {

	String SendSms(String signName, String phoneNumber, String message);
}
