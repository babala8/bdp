package com.zjft.microservice.treasurybrain.datainsight.service;

import com.zjft.microservice.treasurybrain.datainsight.dto.GisPointInfoDTO;

import java.util.Map;


/**
 * @author 杨光
 */
public interface GisService {
	
	void addGisPointInfo(Map<String, Object> params);
	
	GisPointInfoDTO qryGisPointInfo(String orgNo);

}
