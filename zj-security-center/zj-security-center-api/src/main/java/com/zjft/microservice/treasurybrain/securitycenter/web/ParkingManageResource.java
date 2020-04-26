package com.zjft.microservice.treasurybrain.securitycenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author zhangjs
 * @since 2019/9/17 19:53
 */
@Api(value = "安防中心：泊车引导", tags = "安防中心：泊车引导")
public interface ParkingManageResource {

	String PREFIX = "${security-center:}/v2/parkingManage";

	@PostMapping(PREFIX)
	@ApiOperation(value = "泊车引导", notes = "泊车引导")
	DTO parkingGuidePushMessage(@RequestBody Map<String, Object> paramMap);

}
