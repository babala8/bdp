package com.zjft.microservice.treasurybrain.business.web_inner;

import com.zjft.microservice.treasurybrain.business.dto.CurrencyTypeListDTO;
import com.zjft.microservice.treasurybrain.business.dto.TaskInOutDTO;

import java.util.List;
import java.util.Map;

public interface TaskInOutInnerResource {

	String selectSumAmount(Map<String, Object> paramMap);

	List<CurrencyTypeListDTO> selectCurrencyTypeList(Map<String, Object> paramMap);

	List<TaskInOutDTO> selectByTaskNo(String taskNo);

	int insertSelectiveByMap(Map<String, Object> taskInOutMap);

	int deleteByTaskNo(String taskNo);
}
