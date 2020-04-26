package com.zjft.microservice.treasurybrain.tolly.web;

import com.zjft.microservice.handler.annotation.ZjMessageResource;
import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.tolly.service.TollySystemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@ZjMessageResource
@Slf4j
@RestController
public class TollySystemsResourceImpl implements TollySystemResource {
	@Resource
	private TollySystemsService tollySystemsService;

	@Override
	public DTO applyForReceipt(TransferTaskInfoDTO transferTaskInfoDTO) {
		return  tollySystemsService.applyForReceipt(transferTaskInfoDTO);
	}

	@Override
	public DTO applyForTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		return  tollySystemsService.applyForTransfer(transferTaskInfoDTO);
	}

	@Override
	public DTO logisticsInput(String returnCode) {
		return tollySystemsService.logisticsInput(returnCode);
	}

	@Override
	public DTO logisticsOutput(String returnCode) {
		return tollySystemsService.logisticsInput(returnCode);
	}

	@Override
	public DTO storageOutput(String returnCode) {
		return tollySystemsService.logisticsInput(returnCode);
	}

	@Override
	public DTO storageInput(String returnCode) {
		return tollySystemsService.logisticsInput(returnCode);
	}

	@Override
	public DTO clearCenterInput(String returnCode) {
		return null;
	}

	@Override
	public DTO clearCenterOutput(String returnCode) {
		return null;
	}
}
