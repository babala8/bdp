package com.zjft.microservice.treasurybrain.securitycenter.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

/**
 * @author zhangjs
 * @since 2019/9/17 19:53
 */
@Api(value = "安防中心：泊车引导", tags = "安防中心：泊车引导")
public interface ParkingManageFlowResource {

	String PREFIX = "${security-center:}/v2/parkingManageFlow";

	@PostMapping(PREFIX)
	@ApiOperation(value = "泊车引导（工作流）", notes = "泊车引导（工作流）")
	@ZjWorkFlow("parkingGuidePushMessage")
	DTO parkingGuidePushMessage(@RequestBody HashMap<String, Object> paramMap);

}
