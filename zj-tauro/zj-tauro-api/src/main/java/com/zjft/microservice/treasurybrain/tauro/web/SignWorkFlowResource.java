package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "物流中心：虚拟签收(工作流)",tags = "物流中心：虚拟签收(工作流)")
public interface SignWorkFlowResource {
	String PREFIX = "${tauro:}/v2/sign";

	/**
	 * 虚拟签收
	 *
	 * 1.修改任务状态
	 * 2.实物状态
	 * 3.增加操作记录
	 * @param taskTransferDTO
	 * @return
	 */
	@PostMapping(PREFIX)
	@ApiOperation(value = "虚拟签收(工作流)",tags = "虚拟签收(工作流)")
	@ZjWorkFlow("sign")
	DTO sign(@RequestBody TaskTransferDTO taskTransferDTO);
}
