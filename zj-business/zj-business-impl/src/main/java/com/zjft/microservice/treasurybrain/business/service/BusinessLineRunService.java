package com.zjft.microservice.treasurybrain.business.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * @author qfxu
 * 线路运行图管理
 * ,
 */
public interface BusinessLineRunService {

	Map<String, Object> addLineRunMap(String createJsonString) throws ParseException;
	/**
	 * @param clrCenterNo
	 * @param lineNoList
	 * @return
	 */
	Map<String, Object> genLineRunMap(String clrCenterNo, List<String> lineNoList) throws ParseException;

}
