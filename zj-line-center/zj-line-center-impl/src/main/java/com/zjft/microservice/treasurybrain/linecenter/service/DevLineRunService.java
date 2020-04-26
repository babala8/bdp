package com.zjft.microservice.treasurybrain.linecenter.service;

import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * @author qfxu
 * 线路运行图管理
 * ,
 */
public interface DevLineRunService {

	Map<String, Object> qryLineRunMap(String createJsonString);

	Map<String, Object> addLineRunMap(String createJsonString) throws ParseException;

	Map<String, Object> modLineRunMap(LineWorkTableDTO dto);

	Map<String, Object> detailLineRunMap(String createJsonString);

	Map<String, Object> getLineRoute(List<String> devList) throws ParseException;

	int insertSelectiveByMap(Map<String, Object> paramMap);

	int deleteLine(Map<String, Object> paramMap);

	List<String> selectTheYearMonthByLineNo(Map<String, Object> paramMap);

	int insertDetailSelectiveByMap(Map<String, Object> lineRunDevDetailMap);

	int deleteLineDetail(Map<String, Object> paramMap);

	LineScheduleDTO selectByMap(Map<String, Object> paramMap);
}
