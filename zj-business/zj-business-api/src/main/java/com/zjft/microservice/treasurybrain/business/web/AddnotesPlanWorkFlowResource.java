package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "业务中心：加钞计划管理（工作流）",value = "业务中心：加钞计划管理（工作流）")
public interface AddnotesPlanWorkFlowResource {
	String PREFIX = "${business:}/v2";

	@ApiOperation(value = "计划分组信息查询（工作流）",notes = "计划分组信息查询（工作流）")
	@GetMapping(PREFIX + "/group/{addnotesPlanNo}")
	@ZjWorkFlow("groupWorkFlow")
	ObjectDTO qryGroupTsp(@PathVariable("addnotesPlanNo") String addnotesPlanNo);
}
