package com.zjft.microservice.treasurybrain.channelcenter.service;


import com.zjft.microservice.treasurybrain.channelcenter.dto.CountTaskInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2019-10-10
 */
public interface DevCountService {

	/**
	* 分页查询车辆信息列表
	* @param paramMap
	*/
	PageDTO<DevCountDTO> queryDevCountList(Map<String, Object> paramMap);

	/**
	 * @Description 根据该设备编号是否存在
	 * @Param devNo
	 */
	Integer queryByDevNo(String devNo);

	/**
	 * @Description 添加设备信息
	 * @Param
	 */
	DTO addDevCountInfo(DevCountDTO devCountDTO);

	/**
	 * @Description 修改清分机信息
	 * @Param devCountDTO
	 */
	DTO modDevCount(DevCountDTO devCountDTO);

	/**
	 * @Description 删除清分机信息
	 * @Param
	 */

	DTO delDevCountByNo(String devNo);


	/**
	 * 分页清分机状态监控列表
	 * @param paramMap
	 */
	ListDTO<DevCountDTO> queryDevConMonitoring(Map<String, Object> paramMap);

	/**
	 * @Description 查询清分机正在执行情况
	 * @Param
	 */
	ListDTO<DevCountDTO> qryCountTaskNum();

	/**
	 * @Description 分配清分机
	 * @Param
	 */
	DTO allotCount(CountTaskInfoDTO countTaskInfoDTO);

	/**
	 * 清分设备信息查询
	 * @param paramMap
	 * @return
	 */
	ListDTO<DevCountDTO> qryCountDevList(Map<String, Object> paramMap);

	int deleteByDevNo(String devNo);

	int insert(Map<String,Object> record);

	List<String> selectlineNoList(String devNo);

	List selectDctVOList(Map<String, Object> paramMap);

}
