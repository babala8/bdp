package com.zjft.microservice.treasurybrain.param.service;

import com.zjft.microservice.treasurybrain.param.domain.SpdateCoeffKey;
import com.zjft.microservice.treasurybrain.param.dto.SpDateCoeffDTO;

import java.util.Map;

public interface SpDateCoeffService {

	 Map<String, Object> qrySpDateCoeff(String createJsonString);

	Map<String, Object> addSpDateCoeff(String createJsonString);

	Map<String, Object> modSpDateCoeff(String createJsonString);

	Map<String, Object> delSpDateCoeff(String createJsonString);

	SpDateCoeffDTO qrySpDateCoeffByKey(SpdateCoeffKey spdateCoeffKey);

}
