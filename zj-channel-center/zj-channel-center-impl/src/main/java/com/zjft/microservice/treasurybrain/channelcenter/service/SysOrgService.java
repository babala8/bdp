package com.zjft.microservice.treasurybrain.channelcenter.service;

import com.zjft.microservice.treasurybrain.channelcenter.dto.OrgBusinessTimeDTO;
import com.zjft.microservice.treasurybrain.common.dto.*;

import java.util.List;
import java.util.Map;


/**
 * @author 杨光
 */
public interface SysOrgService {

	ListDTO<SysOrgDTO> qryChildrenOrgByAuth(String parentOrgNo);


	ListDTO<SysOrgDTO> qryOrgFuzzyByAuth(String orgName);


	SysOrgDTO qrySysOrgDetailByNo(String orgNo);


	PageDTO<SysOrgDTO> qrySysOrgByPage(Map<String, Object> page);


	ListDTO<SysOrgGradeDTO> qrySysOrgGrade();


	DTO addSysOrg(SysOrgDTO dto);


	DTO modSysOrgById(SysOrgDTO dto);


	DTO delSysOrgById(String no);

	ListDTO<OrgBusinessTimeDTO> qryOrgBusinessTime(String orgNo);

	/**
	 * 修改网点营业时间
	 */
	DTO modOrgBusinessTime(OrgBusinessTimeDTO dto);

	/**
	 * 获取清分中心下所有网点
	 *
	 * @param clrCenterNo 清分中心编号
	 * @return retMap：SysOrgDTO
	 */
	Map<String, Object> getNetpointsByClrCenterNo(String clrCenterNo);

	/**
	 * 在地图中查询网点
	 * @return Map
	 */
	ListDTO qryPointInMap();

	Map<String, Object> getNetpointsWithDevsOfGroup(String createJsonString);

	/**
	 * 根据城市名查找网点列表
	 * @param name 城市名
	 * @return Map
	 */
	ListDTO qryPointsByCityName(String name);



	// -----------------------------------------------
	//                   内部服务，待优化
	// -----------------------------------------------

	List<String> qryOrgRegion(String clrOrgNo);

	List<Map<String, Object>> getNetpointsByClrNo(String clrCenterNo);

	int qryOrgGradeNoByOrgNo(String orgNo);

	int qryClrCenterFlag(String orgNo);

	Map<String, String> qryCenterByNo(String no);

	Map<String, String> qryDevInfoByNo(String devNo);

	List<String> selectOrgNoList(String lineNo,Integer orgGradeNo);

	List<SysOrgDTO> qryNetworksByNetworkLineNo(String clrCenterNo,String networkLineNo);

	String selectLineNo(String customerNo);



}
