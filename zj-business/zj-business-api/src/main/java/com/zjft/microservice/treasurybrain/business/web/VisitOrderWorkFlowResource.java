package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.business.dto.VisitOrderDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "业务中心：上门预约管理(工作流)",value = "业务中心：上门预约管理(工作流)")
public interface VisitOrderWorkFlowResource {
	String PREFIX = "${business:}/v2/visitOrder";

	@PostMapping(PREFIX)
	@ApiOperation(value = "添加预约(工作流)", notes = "添加预约(工作流)")
	@ZjWorkFlow("addVisitOrder")
	DTO addVisitOrder(@RequestBody VisitOrderDTO visitOrderDTO);
}
