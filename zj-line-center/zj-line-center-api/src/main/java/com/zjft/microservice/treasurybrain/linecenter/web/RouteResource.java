package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


/**
 * @author qfxu
 */
@Api(value = "线路中心：网点/设备路程管理", tags = {"线路中心：网点/设备路程管理"})
public interface RouteResource {

	String PREFIX = "${line-center:}/v2/route";

	@GetMapping(PREFIX)
	@ApiOperation(value = "路程信息列表查询", notes = "路程信息列表查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "类型", paramType = "query"),
			@ApiImplicitParam(name = "tactic", value = "导航策略", paramType = "query"),
			@ApiImplicitParam(name = "startPointNo", value = "起点", paramType = "query"),
			@ApiImplicitParam(name = "endPointNo", value = "终点", paramType = "query"),
			@ApiImplicitParam(name = "dataType", value = "数据类型（1-设备，2-网点）", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query"),
	})
	PageDTO<NetpointMatrixDTO> qryNetMatrixByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);


	@GetMapping(PREFIX + "/linkPath")
	@ApiOperation(value = "获取清分中心的已关联路径数", notes = "获取清分中心的已关联路径数]")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "dataType", value = "数据类型（1-设备，2-网点）", paramType = "query"),
	})
	ListDTO qryPathLinked(@ApiIgnore @RequestParam Map<String, Object> paramMap);


	@PostMapping(PREFIX + "/linkPath")
	@ApiOperation(value = "路程信息关联", notes = "路程信息关联")
	DTO linkPath(@RequestBody Map<String, Object> paramMap);


	@GetMapping(PREFIX + "/netpointStatus/{clrCenterNo}")
	@ApiOperation(value = "查询清分中心的是否正在关联", notes = "查询清分中心的是否正在关联")
	@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号",  required = true, paramType = "path")
	ObjectDTO qryClrNetPointMatrixStatus(@PathVariable("clrCenterNo") String clrCenterNo);

}
