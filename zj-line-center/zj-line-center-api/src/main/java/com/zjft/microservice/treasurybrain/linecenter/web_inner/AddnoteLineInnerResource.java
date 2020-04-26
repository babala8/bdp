package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/12/31
 */
public interface AddnoteLineInnerResource {

	/**
	 *
	 * 根据日期查询有线路运行图的线路列表，使用了in线路编号拼接，如果数量过多请采用循环生成
	 * @param map theYearMonth 年月 day 日 lineNos 线路列表
	 * @return
	 */
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
