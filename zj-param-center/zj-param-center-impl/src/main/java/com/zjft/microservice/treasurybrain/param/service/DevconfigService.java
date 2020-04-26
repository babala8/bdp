package com.zjft.microservice.treasurybrain.param.service;

import java.util.Map;

public interface DevconfigService {

	Map<String, Object> getDevconfigByClrNo(String createJsonString) ;

	Map<String,Object> devConfig(String createJsonString);

	Map<String,Object> getIsValidCountsByClrNo(String createJsonString);
	
}
