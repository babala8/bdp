package com.zjft.microservice.treasurybrain.business.web_inner;

import java.util.List;
import java.util.Map;

public interface AddnotesPlanDetailInnerResource {
	/**
	 * 根据addnotesPlanNo查询预测总金额
	 * @param addnotesPlanNo
	 * @return
	 */
	double selectSumAddnoteAmount(String addnotesPlanNo);

	int deleteGroup(String addnotesPlanNo);

	int updateSortNoNull(Map<String, Object> paramMap);

	List<String> getDevPointList(Map<String, Object> paramMap);

	int updateSortNoByNetPoint(Map<String, Object> paramMap);

	int updateSortNoByDevNo(Map<String, Object> paramMap);

	int updateSortByGroupNo(Map<String, Object> paramMap);

	List<Map<String, Object>> getNetPointsOrderBySortNo(Map<String, Object> paramMap);

	List<Map<String, Object>> getPlanGroupNetpoints(Map<String, Object> paramMap);

	List<Map<String, Object>> getLineMsgList(Map<String, Object> paramMap);

	/**
	 * 查询加钞的分组和对应设备数
	 *
	 * @param addnotesPlanNo 加钞计划编号
	 * @return 线路编号和设备数
	 */
	//-------------------业务中心
	List<Map<String,Object>> getDevCountEachGroup(String addnotesPlanNo);

	/**
	 * 查询当前分组下的网点数
	 *
	 * @param  paramMap 加钞计划编号 线路编号
	 * @return 网点数
	 */
	//-------------------业务中心
	int getNetCountInGroupByMap(Map<String, Object> paramMap);

	int updateByMapSelective(Map<String, Object> paramMap);
}
