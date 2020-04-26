package com.zjft.microservice.treasurybrain.task.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.task.dto.TransferTaskInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 韩 通
 * @since 2020-03-01
 */
@Api(value = "任务中心：业务管理", tags = "任务中心：业务管理")
public interface BusinessResource {

	String PREFIX = "${task:}/v2/business";

	/**
	 * 任务单申请
	 */
	@PostMapping(PREFIX+"/transfer")
	@ApiOperation(value = "任务单申请", notes = "网点解现&寄库申请")
	DTO applyForTransfer(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	 * 任务单修改修改
	 */
	@PutMapping(PREFIX+"/transfer")
	@ApiOperation(value = "任务单修改", notes = "任务单修改")
	DTO modTransfer(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);
}
