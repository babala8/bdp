package com.zjft.microservice.treasurybrain.datainsight.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 杨光
 * @since 2019-04-01
 */
@Api(value = "数据洞察：多维分析", tags = {"数据洞察：多维分析"})
public interface SaikuResource {

	String PREFIX = "${data-insight:}/v2/saikuToken";

	@GetMapping(value = PREFIX + "/{no}")
	@ApiOperation(value = "saiku", notes = "saikuToken")
	@ApiImplicitParam(name = "no", value = "no", paramType = "path")
	DTO getSaikuTokenByNo(@PathVariable(value = "no") String no);


}
