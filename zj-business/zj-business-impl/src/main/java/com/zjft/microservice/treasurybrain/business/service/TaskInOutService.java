package com.zjft.microservice.treasurybrain.business.service;

import com.zjft.microservice.treasurybrain.business.dto.CurrencyTypeListDTO;
import com.zjft.microservice.treasurybrain.business.dto.TaskInOutDTO;

import java.util.List;
import java.util.Map;

public interface TaskInOutService {
	String selectSumAmount(Map<String, Object> paramMap);

	List<CurrencyTypeListDTO> selectCurrencyTypeList(Map<String, Object> paramMap);

	List<TaskInOutDTO> selectByTaskNo(String taskNo);

	int insertSelectiveByMap(Map<String, Object> taskInOutMap);

	int deleteByTaskNo(String taskNo);
}
