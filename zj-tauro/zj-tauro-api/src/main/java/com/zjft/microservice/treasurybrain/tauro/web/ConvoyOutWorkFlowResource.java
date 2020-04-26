package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "物流中心：经警接库（工作流）",value = "物流中心：经警接库（工作流）")
public interface ConvoyOutWorkFlowResource {
	String PREFIX = "${tauro:}/v2/convoyOut";

	/**
	 *
	 * 1.根据人员编号查询任务单任务单编号
	 * 2.更新任务单消息
	 * @param taskTransferDTO
	 * @return
	 */
	@ApiOperation(value = "物流中心：经警接库(工作流)",notes = "物流中心：经警接库(工作流)")
	@PostMapping(path = PREFIX )
	@ZjWorkFlow("convoyOut")
	DTO recover(@RequestBody TaskTransferDTO taskTransferDTO);
}
