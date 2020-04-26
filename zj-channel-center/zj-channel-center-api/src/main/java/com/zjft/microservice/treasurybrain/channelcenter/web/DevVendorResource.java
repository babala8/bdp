package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevVendorDTO;
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
@Api(value = "渠道中心：设备品牌管理", tags = {"渠道中心：设备品牌管理"})
public interface DevVendorResource {

	String PREFIX = "${channel-center:}/v2/dev/vendor";


	@GetMapping(PREFIX)
	@ApiOperation(value = "查询设备品牌列表", notes = "查询设备品牌列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "品牌名称", paramType = "query"),
	})
	PageDTO<DevVendorDTO> queryDevVendorList(@RequestParam(defaultValue = "") String name);


	@PostMapping(PREFIX)
	@ApiOperation(value = "新增设备品牌", notes = "{\"address\":\"\",hotLine1\":\"\",\"name\":\"\",\"status\":\"\"}")
	DTO addDevVendor(@RequestBody DevVendorDTO dto);


	@PutMapping(PREFIX)
	@ApiOperation(value = "修改设备品牌", notes = "{\"no\":\"\",\"address\":\"\",hotLine1\":\"\",\"name\":\"\",\"status\":\"\"}")
	DTO modDevVendor(@RequestBody DevVendorDTO dto);


	@DeleteMapping(PREFIX + "/{no}")
	@ApiOperation(value = "删除设备品牌", notes = "删除设备品牌")
	@ApiImplicitParam(name = "no", value = "参数no", required = true, paramType = "path")
	DTO delDevVendorByNo(@PathVariable("no") String no);

}
