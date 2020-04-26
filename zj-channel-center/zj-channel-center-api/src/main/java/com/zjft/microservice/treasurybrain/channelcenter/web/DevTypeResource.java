package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevTypeDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杨光
 * @since 2019-04-02
 */
@Api(value = "渠道中心：设备型号管理", tags = {"渠道中心：设备型号管理"})
public interface DevTypeResource {

	String PREFIX = "${channel-center:}/v2/dev/type";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询设备型号列表", notes = "查询设备型号列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "型号名称", paramType = "query"),
	})
	PageDTO<DevTypeDTO> queryDevTypeList(@RequestParam(defaultValue = "") String name);


	@PostMapping(PREFIX)
	@ApiOperation(value = "新增设备型号", notes = "新增设备型号")
	DTO addDevType(@RequestBody DevTypeDTO dto);


	@PutMapping(PREFIX)
	@ApiOperation(value = "修改设备型号", notes = "修改设备型号")
	DTO modDevType(@RequestBody DevTypeDTO dto);


	@DeleteMapping(PREFIX + "/{no}")
	@ApiOperation(value = "删除设备型号", notes = "删除设备型号")
	@ApiImplicitParam(name = "no", value = "参数no", required = true, paramType = "path")
	DTO delDevTypeByNo(@PathVariable("no") String no);
}
