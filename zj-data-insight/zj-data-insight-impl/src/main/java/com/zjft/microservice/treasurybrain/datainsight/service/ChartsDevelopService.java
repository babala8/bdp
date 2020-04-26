package com.zjft.microservice.treasurybrain.datainsight.service;

import java.util.List;
import java.util.Map;


public interface ChartsDevelopService {
	Map<String, Object> preview(String jsonParams);

	void addChartsModel(Map<String, Object> params);

	Map<String, Object> getSelfDefCharts();

	Map<String, Object> queryChartSubjects();

	List<Object> qryServiceList();

}
