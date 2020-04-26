package com.zjft.microservice.treasurybrain.channelcenter.service;

import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import com.zjft.microservice.treasurybrain.common.dto.ClrCenterDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;

import java.util.List;
import java.util.Map;


/**
 * @author 常健
 */
public interface ClrCenterService {

	ListDTO<ClrCenterDTO> qryClrCenterListByAuth(Map<String, Object> paramMap);

	// -----------------------------------------------
	//                   内部服务，待优化
	// -----------------------------------------------

	List<ClrCenterTable> getClrCenterListByOrgNo(String orgNo);

	List<Map<String, Object>> getClrCenterByClrNo(String clrCenterNo);

	ClrCenterTable selectByPrimaryKey(String clrCenterNo);

	List<String> getClrCenterOrgNo(String clrCenterNo);

	List<String> clrCenterNoList();

	String getOrgNameByClrNo(String centerNo);

	void updateByPrimaryKeySelective(ClrCenterTable clrCenterTable);

	DTO updateCenterNum(ClrCenterDTO clrCenterDTO);

	Boolean qryClrCenterIsAuto(String clrCenterNo);


	/**
	 * 获取清分中心详细信息
	 * @param clrCenterNo
	 * @return
	 */
	Map<String, Object> getClrCenterByClrCenterNo(String clrCenterNo);

	/**
	 * 获取清分中心下所有网点坐标信息
	 * @param clrCenterNo
	 * @return
	 */
	Map<String, Object> getNetpointsByClrNo(String clrCenterNo);
}
