package com.zjft.microservice.treasurybrain.linecenter.service;

import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/12/10
 */
public interface DevLineService {

	/**
	 * 规划两点间线路
	 *
	 * @param jsonParam clrOrgNo:清机中心机构编号,originX:起点经度,originY:起点纬度,destinationX:终点经度
	 *                  destinationY:终点纬度,mode:导航模式,tactic:导航策略
	 * @return retMap:routes
	 */
	Map<String, Object> directionRoute(String jsonParam);

	Map<String, Object> qryPlanGroupRoute(String createJsonString);

	Map<String, Object> autoGroupTsp(String jsonParam);

	Map<String, Object> modGroupTsp(String createJsonString);

	/**
	 * 线路规划
	 */
	List<Object> sortPlanGroup(String addnotesPlanNo, String groupNo, int tactic, int dataType);

	/**
	 * 查询加钞线路
	 * @param string
	 * @return
	 */
	Map<String, Object> qryAddnotesLineByPage(String string);

	/**
	 * 删除加钞线路
	 * @param string
	 * @return
	 */
	Map<String, Object> delAddnotesLine(String string);

	/**
	 * 添加加钞线路
	 * @param string
	 * @return
	 */
	Map<String, Object> addAddnotesLine(String string);

	/**
	 * 修改加钞线路
	 * @param string
	 * @return
	 */
	Map<String,Object> modAddnotesLine(String string);

	/**
	 * 查询加钞线路详细信息
	 * @param string
	 * @return
	 */
	Map<String,Object> qryAddnotesLineDetail(String string);

	Map<String, Object> qryLineListByDateAndClrNo(String clrCenterNo);

}
