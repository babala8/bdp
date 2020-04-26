package com.zjft.microservice.treasurybrain.datainsight.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;

import java.util.Map;


public interface ChartService {

	Map<String, Object> qryCharts(Map<String, Object> paramsMap);

	ListDTO queryChartCreators();

	DTO delChart(String id);

	DTO modChartsModel(Map<String, Object> params);

	Map<String, Object> getChartById(String id);
}
