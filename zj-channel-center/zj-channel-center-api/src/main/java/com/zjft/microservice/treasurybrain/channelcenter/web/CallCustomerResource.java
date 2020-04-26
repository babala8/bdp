package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTypeDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author zhangjs
 * @since 2019-09-21
 */
@Api(value = "渠道中心：上门客户管理", tags = {"渠道中心：上门客户管理"})
public interface CallCustomerResource {

	String PREFIX = "${channel-center:}/v2/callCustomer";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询上门客户分页列表", notes = "查询上门客户分页列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "callCustomerType", value = "客户类型", paramType = "query"),
			@ApiImplicitParam(name = "customerShortName", value = "客户简称", paramType = "query"),
			@ApiImplicitParam(name = "customerNumber", value = "客户号", paramType = "query"),
			@ApiImplicitParam(name = "isOneself", value = "是否为本行用户", paramType = "query"),
			@ApiImplicitParam(name = "address", value = "地址", paramType = "query"),
			@ApiImplicitParam(name = "callCustomerLine", value = "线路", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, paramType = "query"),
	})
	PageDTO<CallCustomerDTO> queryCallCustomerList(@ApiIgnore @RequestParam Map<String, Object> params);

	@PostMapping(PREFIX)
	@ApiOperation(value = "新增上门客户", notes = "新增上门客户")
	DTO addCallCustomer(@RequestBody CallCustomerDTO dto);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改上门客户", notes = "修改上门客户")
	DTO modCallCustomer(@RequestBody CallCustomerDTO dto);

	@DeleteMapping(PREFIX + "/{customerNo}")
	@ApiOperation(value = "删除上门客户", notes = "删除上门客户")
	@ApiImplicitParam(name = "customerNo", value = "参数customerNo", required = true, paramType = "path")
	DTO delCallCustomerByNo(@PathVariable("customerNo") String no);

	@GetMapping(PREFIX+ "/type")
	@ApiOperation(value = "分页查询客户类型列表", notes = "分页查询客户类型列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "类型名称", paramType = "query"),
			@ApiImplicitParam(name = "no", value = "类型编号", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query"),
	})
	PageDTO<CallCustomerTypeDTO> queryCallCustomerTypeListByPage(@ApiIgnore @RequestParam Map<String, Object> params);

	@GetMapping(PREFIX+ "/typeList")
	@ApiOperation(value = "查询客户类型列表", notes = "查询客户类型列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "类型名称", paramType = "query"),
			@ApiImplicitParam(name = "no", value = "类型编号", paramType = "query"),
	})
	ListDTO<CallCustomerTypeDTO> queryCallCustomerTypeList(@ApiIgnore @RequestParam Map<String, Object> params);
}
