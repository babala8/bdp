package com.zjft.microservice.treasurybrain.business.service;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;

import java.util.List;
import java.util.Map;

/**
 * @author 韩通
 * @since 2019/11/14
 */
public interface NotesReceiptService {

	/**
	 * 钞处领现申请
	 */
	Map<String, Object> applyForNotesReceipt(TransferTaskInfoDTO transferTaskInfoDTO);


	/**
	 * 钞处领现修改
	 */
	DTO modNotesReceipt(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 钞处解现申请
	 */
	Map<String, Object> transferForClearCenter(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 钞处解现修改
	 */
	DTO modForTransfer(TransferTaskInfoDTO transferTaskInfoDTO);

}
