package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "业务中心：个性业务管理",value = "业务中心：个性业务管理")
public interface SelfProductWorkFlowResource {
	String PREFIX = "${business:}/v2/selfProduct";
    //已改
	@PostMapping(PREFIX)
	@ApiOperation(value = "产品业务申请", notes = "产品业务申请")
	@ZjWorkFlow("applyForSelfProduct")
	DTO applyForSelfProduct(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);
}
