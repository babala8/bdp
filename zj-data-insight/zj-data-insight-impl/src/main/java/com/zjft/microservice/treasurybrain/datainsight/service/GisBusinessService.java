package com.zjft.microservice.treasurybrain.datainsight.service;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;


/**
 * @author 杨光
 */
public interface GisBusinessService {

	/**
	 * 在地图中查询网点
	 * @return Map
	 */
	ListDTO qryPointInMap();

	/**
	 * 根据城市名查找网点列表
	 * @param name 城市名
	 * @return Map
	 */
	ListDTO qryPointsByCityName(String name);

	/*
	 * 根据设备号查找case列表
	 * @param atmCode  设备号
	 * @return Map
	Map<String, Object> qryCaseListByAtm(String atmCode);*/

	/*
	 * 根据设备号查找case类型
	 * @param parseString  设备号
	 * @return Map
	Map<String, Object> qryCaseTypesByAtm(String parseString);*/

	/*
	 * 根据机构号查询设备信息
	 * @param parseString  机构号
	 * @return Map
	Map<String, Object> qryDevsInfoByOrgNo(String parseString);*/

	/*
	 * 根据指定信息查询查询排名信息
	 * @param createJsonString 指定信息
	 * @return Map
	Map<String, Object> qryRanking(String createJsonString); */


//	Map<String, Object> invokeService(String id, Map<String, Object> paramMap);


//	Map<String, Object> qryDevTypesForView(String jsonParam);

}
