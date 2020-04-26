package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 崔耀中
 * @since 2020-01-03
 */
public interface DevServiceCompanyInnerResource {

	@ApiOperation(value = "修改设备服务商", notes = "修改设备维护商")
	String qryNoByName(@RequestParam("name") String name);

}
