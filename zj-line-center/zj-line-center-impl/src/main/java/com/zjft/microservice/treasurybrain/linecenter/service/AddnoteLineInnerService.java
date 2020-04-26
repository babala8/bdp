package com.zjft.microservice.treasurybrain.linecenter.service;

import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/12/31
 */
public interface AddnoteLineInnerService {

	 List<String> qryLineHasMapWithDate(Map<String, Object> map);

	 String qryLineNameByLineNo(String lineNo);

	int deleteByPrimaryKey(String lineNo);

	int insert(Map<String, Object> map);

	LineTableDTO selectByPrimaryKey(String lineNo);

	List<Map<String, Object>> qryClrCenter(String lineNo);

	int updateByMap(Map<String,Object> map);

	List<LineTableDTO> qryLineListByMap(Map<String, Object> paramMap);

	List<LineTableDTO> getLineListByDateAndClrNo(Map<String, Object> paramMap);

	List<LineTableDTO> qryAllInfo();

	int  qryTotalRowPlan(Map<String, Object> paramMap);

	List<LineTableDTO> rowSetList(Map<String, Object> paramMap);

	List<LineTableDTO> rowSetList1(Map<String, Object> paramMap);

	List<String> getLineNosWithTypeAndClrNo(Map<String,Object> paramsMap);
}
