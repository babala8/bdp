package com.zjft.microservice.treasurybrain.pushserver.util;

/**
 * 短信发送测试模拟接口
 * @author 常 健
 * @since 2020/2/21
 */
public class SmsTestImpl implements SmsTest{


	@Override
	public String SendSms(String signName, String phoneNumber, String message) {
		try {
			//具体实现
			return "OK";
		}catch (Exception e){
			return "EXCEPTION";
		}
	}
}
