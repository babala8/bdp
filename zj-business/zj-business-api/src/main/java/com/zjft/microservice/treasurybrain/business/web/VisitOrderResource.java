package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.treasurybrain.business.dto.VisitOrderDTO;
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
 * @author 吴朋
 * @since 2019/9/21
 */
@Api(tags = "业务中心：上门预约管理",value = "业务中心：上门预约管理")
public interface VisitOrderResource {
	String PREFIX = "${business:}/v2/visitOrder";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询预约", notes = "查询预约")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "customerNumber", value = "上门客户号", paramType = "query"),
			@ApiImplicitParam(name = "startDate", value = "开始预约日期", paramType = "query"),
			@ApiImplicitParam(name = "endDate", value = "结束预约日期", paramType = "query"),
			@ApiImplicitParam(name = "orderTimePeriod", value = "预约时间段", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", paramType = "query")
	})
	PageDTO<VisitOrderDTO> qryVisitOrder(@ApiIgnore @RequestParam Map<String, Object> paramMap);

//	@PostMapping(PREFIX)
//	@ApiOperation(value = "添加预约", notes = "添加预约")
//	DTO addVisitOrder(@RequestBody VisitOrderDTO visitOrderDTO);

	@DeleteMapping(PREFIX)
	@ApiOperation(value = "删除预约", notes = "删除预约")
	@ApiImplicitParams({
	@ApiImplicitParam(name = "customerNumber", value = "预约人员号",required = true,paramType = "query"),
	@ApiImplicitParam(name = "orderDate", value = "预约日期", required = true,paramType = "query")
	})
	DTO deleteVisitOrder(@RequestParam String customerNumber,@RequestParam String orderDate);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改预约", notes = "修改预约")
	DTO updateVisitOrder(@RequestBody VisitOrderDTO visitOrderDTO);


	@PutMapping(PREFIX+"/audit")
	@ApiOperation(value = "审核预约", notes = "审核预约")
	DTO auditVisitOrder(@RequestBody VisitOrderDTO visitOrderDTO);

	@GetMapping(PREFIX+"/orderCustomers")
	@ApiOperation(value = "查询上门预约客户列表",notes = "查询上门预约客户列表")
	@ApiImplicitParams({
	})
	ListDTO qryOrderCustomers();

}
