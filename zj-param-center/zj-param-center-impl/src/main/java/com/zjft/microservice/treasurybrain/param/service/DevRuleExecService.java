package com.zjft.microservice.treasurybrain.param.service;

import com.zjft.microservice.treasurybrain.param.domain.DevRuleExecKey;
import com.zjft.microservice.treasurybrain.param.dto.DevRuleExecDTO;

import java.util.Map;

public interface DevRuleExecService {

	Map<String,Object> addDevRuleExec(String createJsonString);

	Map<String, Object> qryDevRuleExec(String createJsonString);

	Map<String,Object> delSpecialRuleExec(String createJsonString);

	Map<String, Object> updateDevRuleExecStatus(String jsonParam);

	DevRuleExecDTO qryDevRuleExecByKey(DevRuleExecKey devRuleExecKey);

}
