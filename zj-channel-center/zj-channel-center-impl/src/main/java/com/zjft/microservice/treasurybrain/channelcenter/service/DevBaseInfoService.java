package com.zjft.microservice.treasurybrain.channelcenter.service;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevBaseInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevClrTimeParamListDTO;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.DevCatalogDTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import java.util.List;
import java.util.Map;


/**
 * @author 徐全发
 * @author 杨光
 * @since 2019-03-20
 */
public interface DevBaseInfoService {

	ListDTO<DevBaseInfoDTO> qryDevInfoByClrNo(String paramName);


	PageDTO<DevBaseInfoDTO> queryDevInfoList(Map<String, Object> params);


	DTO addDevInfo(DevBaseInfoDTO dto);


	DTO modDevInfo(DevBaseInfoDTO dto);


	DTO delDevInfoByNo(String no);


	ListDTO<DevCatalogDTO> qryDevCatalog();

	/**
	 * 查询设备信息及使用信息
	 *
	 * @param string devNo：设备编号，orgNo：所属机构
	 * @return entity：设备编号，地址，组织机构名，剩余可用钞量，日均取款量，剩余可用天数，距离上次加钞天数，查询最近两个月清机周期中的日均取款量
	 */
	Map<String, Object> getDevBaseInfoByNo(String string);

	/**
	 *
	 * 查询单台设备的清机周期信息
	 *
	 * @param devNo 设备编号
	 * @return
	 */
	DevClrTimeParamListDTO qryByDevNo(String devNo);


	// -------------------------------------------------------
	// 					内部服务，待优化
	// ------------------------------------------------------

	DevBaseInfo selectByPrimaryKey(String no);


	DevBaseInfo qryDevByNoOrgNo(String devNo, String orgNo);


	DevBaseInfo selectDetailByPrimaryKey(String devNo);

	void updateByPrimaryKeySelective(DevBaseInfo devBaseInfo);

	List<Map<String, Object>> getKeyEventDevice(Map<String, Object> params);

	List<Map<String, Object>> getKeyEventDeviceForfault(Map<String, Object> params);

	List<Map<String, Object>> getKeyEventDeviceForLineRun(Map<String, Object> params);

	List<Map<String, Object>> getAvaileAmtAndTimeInterval(Map<String, Object> params);

}
