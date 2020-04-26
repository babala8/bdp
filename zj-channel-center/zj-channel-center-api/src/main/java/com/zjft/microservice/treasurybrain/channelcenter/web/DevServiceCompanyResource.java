package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevServiceCompanyDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨光
 * @since 2019-04-02
 */
@Api(value = "渠道中心：设备服务商管理", tags = {"渠道中心：设备服务商管理"})
public interface DevServiceCompanyResource {

	String PREFIX = "${channel-center:}/v2/dev/company";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询设备服务商列表", notes = "查询设备维护商列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "设备服务商名称", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "设备服务商类型", paramType = "query")
	})
	PageDTO<DevServiceCompanyDTO> queryDevCompanyList(@RequestParam(defaultValue = "") String name,@RequestParam(defaultValue = "") String type);


	@PostMapping(PREFIX)
	@ApiOperation(value = "新增设备服务商", notes = "新增设备维护商")
	DTO addDevCompany(@RequestBody DevServiceCompanyDTO dto);


	@PutMapping(PREFIX)
	@ApiOperation(value = "修改设备服务商", notes = "修改设备维护商")
	DTO modDevCompany(@RequestBody DevServiceCompanyDTO dto);


	@DeleteMapping(PREFIX + "/{no}")
	@ApiOperation(value = "删除设备服务商", notes = "删除设备维护商")
	@ApiImplicitParam(name = "no", value = "参数no", required = true, paramType = "path")
	DTO delDevCompanyByNo(@PathVariable("no") String no);

	/**
	 * 批量导入服务商
	 */
	@PostMapping(PREFIX+ "/import")
	@ApiOperation(value = "批量导入服务商信息", notes = "批量导入服务商信息")
	DTO importCompany(@RequestParam(value="file") MultipartFile file);
}
