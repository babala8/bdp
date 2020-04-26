package com.zjft.microservice.treasurybrain.linecenter.service;

import java.util.Map;

public interface RouteService {

	public Map<String, Object> qryNetMatrixByPage(String createJsonString) ;

	public Map<String, Object> qryPathLinked(Map<String, Object> paramMap);

	public Map<String, Object> linkPath(String createJsonString);

	public Map<String, Object> qryClrNetPointMatrixStatus(
			String createJsonString);
	
}
