package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 葛瑞莲
 * @since 2019/9/17
 */
@Api(tags = "业务中心：网点业务管理", value = "业务中心：网点业务管理")
public interface NetBusinessResource {
	String PREFIX = "${business:}/v2/net";

	/**
	 * 现金调拨申请 已改
	 */
	@PostMapping(PREFIX+"/cash")
	@ApiOperation(value = "现金调拨申请", notes = "现金调拨申请")
	DTO applyForCashTransfer(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 修改现金调拨单 已改
	 */
	@PutMapping(PREFIX+"/cash")
	@ApiOperation(value = "现金调拨修改", notes = "现金调拨修改")
	DTO modCashTransfer(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);

	/** 已改
	 * 网点解现&寄库申请
	 */
	@PostMapping(PREFIX+"/transfer")
	@ApiOperation(value = "网点解现&寄库申请", notes = "网点解现&寄库申请")
	DTO applyForTransfer(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);

	/**已改
	 * 网点解现&寄库修改
	 */
	@PutMapping(PREFIX+"/transfer")
	@ApiOperation(value = "网点解现&寄库修改", notes = "网点解现&寄库修改")
	DTO modTransfer(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);


	/**
	 * 网点领现申请 已改
	 */
	@PostMapping(PREFIX+"/receipt")
	@ApiOperation(value = "网点领现申请", notes = "网点领现申请")
	DTO applyForReceipt(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 网点领现修改 已改
	 */
	@PutMapping(PREFIX+"/receipt")
	@ApiOperation(value = "网点领现修改", notes = "网点领现修改")
	DTO modReceipt(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);

}
