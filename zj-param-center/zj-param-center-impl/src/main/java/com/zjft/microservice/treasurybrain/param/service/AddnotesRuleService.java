package com.zjft.microservice.treasurybrain.param.service;

import com.zjft.microservice.treasurybrain.param.dto.AddnotesRuleDTO;

import java.util.Map;

public interface AddnotesRuleService {

	Map<String, Object> qryAddnotesRule(String createJsonString);

	Map<String, Object> addAddnotesRule(String createJsonString);

	Map<String, Object> modAddnotesRule(String createJsonString);

	AddnotesRuleDTO detailAddnotesRule(String createJsonString);

	Map<String,Object> delAddnotesRule(String createJsonString);

}
