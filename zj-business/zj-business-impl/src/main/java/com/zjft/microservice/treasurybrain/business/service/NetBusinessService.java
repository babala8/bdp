package com.zjft.microservice.treasurybrain.business.service;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;

import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/17
 */
public interface NetBusinessService {
	/**
	 * 现金调拨申请
	 *
	 * @param transferTaskInfoDTO
	 * @return
	 */
	Map<String, Object> applyForCashTransfer(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 现金调拨修改
	 *
	 * @param transferTaskInfoDTO
	 * @return
	 */
	DTO modCashTransfer(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 网点寄库/解现申请
	 */
	Map<String, Object> applyForTransfer(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 网点寄库/解现修改
	 */
	DTO modForTransfer(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 网点领现申请
	 */
	Map<String, Object> applyForReceipt(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 网点领现修改
	 */
	DTO modReceipt(TransferTaskInfoDTO transferTaskInfoDTO);
}
