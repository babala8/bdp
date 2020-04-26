package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.common.dto.ClrCenterDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 杨光
 */
@Api(value = "渠道中心：金库管理", tags = {"渠道中心：金库管理"})
public interface ClrCenterResource {

	String PREFIX = "${channel-center:}/v2/clrCenter";

	@GetMapping(PREFIX)
	@ApiOperation(value = "根据用户权限查询金库列表", notes = "根据用户权限查询金库列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "金库编号", paramType = "query"),
			@ApiImplicitParam(name = "clrCenterName", value = "金库名称", paramType = "query"),
			@ApiImplicitParam(name = "clrCenterType", value = "金库类型", paramType = "query",dataType = "Integer"),
	})
	ListDTO<ClrCenterDTO> qryClrCenterListByAuth(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PutMapping(PREFIX + "/updateCenterNum")
	@ApiOperation(value = "（金库参数调整）", notes = "（金库参数调整）")
	DTO updateCenterNum(@RequestBody ClrCenterDTO clrCenterDTO);

	@GetMapping(PREFIX + "/qryClrCenter/{clrCenterNo}")
	@ApiOperation(value = "获取清分中心详细信息", notes = "获取清分中心详细信息")
	@ApiImplicitParam(name = "clrCenterNo", value = "清分中心编号", required = true, paramType = "path")
	ObjectDTO getClrCenterByNo(@PathVariable("clrCenterNo") String clrCenterNo);


}

