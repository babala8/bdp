package com.zjft.microservice.treasurybrain.securitycenter.service;

import java.text.ParseException;
import java.util.Map;


/**
 * @author zhangjs
 * 预警信息处理
 * @since 2019/9/08 18:51
 */
public interface SampleMessageService {

	Map<String, Object> addSampleMessageMap(String createJsonString) throws ParseException;

	Map<String, Object> parkingGuidePushMessage(String createJsonString) throws ParseException;

}
