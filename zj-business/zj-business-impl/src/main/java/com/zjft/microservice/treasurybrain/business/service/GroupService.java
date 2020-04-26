package com.zjft.microservice.treasurybrain.business.service;

import java.util.List;
import java.util.Map;

public interface GroupService {

   Map<String, Object> qryGroupsTsp(String createJsonString);

   Map<String, Object> qryGroupByNo(String createJsonString);


   Map<String, Object> modGroup(String createJsonString);

	/**
	 * 线路规划
	 */
   List<Object> sortPlanGroup(String addnotesPlanNo, String groupNo, int tactic, int dataType);

}
