package com.zjft.microservice.treasurybrain.securitycenter.service;

import java.util.Map;


/**
 * @author zhangjs
 * 安防预警信息管理
 * @since 2019/9/17 18:51
 */
public interface SecurityWarnService {

	Map<String, Object> qrySecurityWarnInfo(String createJsonString);

}
