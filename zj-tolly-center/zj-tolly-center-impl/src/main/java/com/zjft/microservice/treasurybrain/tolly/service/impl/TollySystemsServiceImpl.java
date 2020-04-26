package com.zjft.microservice.treasurybrain.tolly.service.impl;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.tolly.service.TollySystemsService;
import com.zjft.microservice.treasurybrain.tolly.util.TollySystemsEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TollySystemsServiceImpl implements TollySystemsService {

	@Override
	public DTO applyForReceipt(TransferTaskInfoDTO transferTaskInfoDTO) {
//		TransferTaskInfoDTO transferTaskInfoDTO = new TransferTaskInfoDTO();
//		String clrCenterNo = StringUtil.parseString(json.get("clrCenterNo"));
//		int customerType = StringUtil.objectToInt(json.get("customerType"));
//		String planFinishDate = StringUtil.parseString(json.get("planFinishDate"));
//		String note = StringUtil.parseString(json.get("note"));
//		int taskType = StringUtil.objectToInt(json.get("taskType"));
//		String customerNo = StringUtil.parseString(json.get("customerNo"));
//		List<TransferTaskDetailDTO> transferTaskDetailDTO = (List<TransferTaskDetailDTO>)json.get("transferTaskDetailDTO");
//		transferTaskInfoDTO.setClrCenterNo(clrCenterNo);
//		transferTaskInfoDTO.setCustomerNo(customerNo);
//		transferTaskInfoDTO.setCustomerType(customerType);
//		transferTaskInfoDTO.setPlanFinishDate(planFinishDate);
//		transferTaskInfoDTO.setNote(note);
//		transferTaskInfoDTO.setTaskType(taskType);
//		transferTaskInfoDTO.setTransferTaskDetailDTO(transferTaskDetailDTO);
//		DTO dto = receiptResource.applyForReceipt(transferTaskInfoDTO);
		return null;
	}

	@Override
	public DTO applyForTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
//		DTO dto = transferResource.applyForTransfer(transferTaskInfoDTO);
		return null;
	}

	@Override
	public DTO logisticsInput(String returnCode) {
		return new DTO(RetCodeEnum.SUCCEED.getCode(), TollySystemsEnum.getTipByCode(returnCode));
	}




}
