package com.zjft.microservice.treasurybrain.task.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.task.dto.*;

import java.util.Map;


public interface TaskDetailService {

	ListDTO<String> qryPreviousContainByCustomerNo(String customerNo);


	/**
	 * 网点调拨审核（网点管理员、金库人员）
	 */
	DTO modDevClrTime(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 清点信息详情查询
	 * @param taskNo 任务编号
	 * @return
	 */
	TaskDTO qryLoadNoteDetail(String taskNo);

	/**
	 * 清点信息分页查询
	 * @return
	 */
	PageDTO<LoadNoteDTO> qryLoadNoteByPage(Map<String, Object> paramMap) ;

	/**
	 * 分页查询配钞信息
	 * @return
	 */
	PageDTO<LoadNoteDTO> qryLoadInfoByPage(Map<String, Object> paramMap) ;

	/**
	 * 配钞信息查询
	 * @param taskNo 任务编号
	 * @return
	 */
	LoadNoteDTO qryLoadInfo(String taskNo);

	/**
	 * 分页查询配钞清分信息
	 * @return
	 */
	PageDTO<TaskCountInfoDTO> qryCountTaskListByPage(Map<String, Object> paramMap) ;

}
