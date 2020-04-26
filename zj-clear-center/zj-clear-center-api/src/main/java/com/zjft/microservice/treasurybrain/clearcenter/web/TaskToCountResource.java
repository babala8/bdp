package com.zjft.microservice.treasurybrain.clearcenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


/**
 * @author zhangjs
 * @since 2019/10/10 15:17
 */
@Api(value = "钞处中心：计划配钞清点",tags = "钞处中心：计划配钞清点")
public interface TaskToCountResource {

	String PREFIX = "${clear-center:}/v2/allocationAndInventory";

	@PostMapping(PREFIX)
	@ApiOperation(value = "配钞清分", notes = "配钞清分")
	DTO countTask(@RequestBody Map<String, Object> paramMap);

}
