package com.zjft.microservice.treasurybrain.datainsight.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import java.util.Map;


public interface DetailQueryManageService {

	PageDTO qryDetailQuery(Map<String, Object> paramsMap);

	DTO delQueryById(String no);
}
