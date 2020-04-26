package com.zjft.microservice.treasurybrain.productcenter.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceDTO;
import com.zjft.microservice.treasurybrain.productcenter.dto.ServiceStatusConvertDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "产品中心：服务基本信息(工作流)",value = "产品中心：服务基本信息(工作流)")
public interface ServiceWorkFlowResource {
	String PREFIX = "${product-center:}/v2/serviceInfo";
	@PostMapping(PREFIX)
	@ApiOperation(value = "增加服务信息", notes = "增加服务信息")
	@ZjWorkFlow("addService")
	DTO addProductInfo(@RequestBody ServiceDTO serviceDTO);

	@PutMapping(PREFIX+"/status")
	@ApiOperation(value = "修改服务状态", notes = "修改服务状态信息")
	@ZjWorkFlow("modService")
	DTO modProductStatus(@RequestBody ServiceDTO serviceDTO);

	@PostMapping(PREFIX+"/statusConvert")
	@ApiOperation(value = "增加服务状态节点信息", notes = "增加服务状态节点信息")
	@ZjWorkFlow("addServiceStatusConver")
	DTO addProductStatusConverInfo(@RequestBody ServiceStatusConvertDTO serviceStatusConvertDTO);
}
