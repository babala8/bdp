package com.zjft.microservice.treasurybrain.task.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.task.dto.TransferTaskInfoDTO;

import java.util.Map;

/**
 * @author 韩 通
 * @since 2020-03-01
 */
public interface BusinessService {

	/**
	 * 任务单申请
	 */
	Map<String, Object> applyForTransfer(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 任务单修改
	 */
	DTO modForTransfer(TransferTaskInfoDTO transferTaskInfoDTO);
}
