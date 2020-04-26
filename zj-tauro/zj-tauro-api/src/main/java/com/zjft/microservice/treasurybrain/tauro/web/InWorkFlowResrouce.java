package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "物流中心：入库交接",tags = "物流中心：入库交接")
public interface InWorkFlowResrouce {
	String PREFIX = "${tauro:}/v2/in";

	/**
	 * 入库
	 * @return
	 */
	@ApiOperation(value = "物流中心：入库交接",notes = "物流中心：入库交接")
	@PostMapping(path = PREFIX )
	@ZjWorkFlow("in")
	DTO in(@RequestBody TaskTransferDTO taskTransferDTO);
}
