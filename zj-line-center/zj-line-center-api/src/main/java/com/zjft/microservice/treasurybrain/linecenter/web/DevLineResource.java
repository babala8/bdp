package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
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
@Api(value = "线路中心：设备线路管理", tags = {"线路中心：设备线路管理"})
public interface DevLineResource {

	String PREFIX = "${line-center:}/v2/devLine";


	@GetMapping(PREFIX + "/group/directionRoute")
	@ApiOperation(value = "规划两点间线路", notes = "规划两点间线路")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "originX", value = "起点经度", required = true, paramType = "query"),
			@ApiImplicitParam(name = "originY", value = "起点纬度", required = true, paramType = "query"),
			@ApiImplicitParam(name = "destinationX", value = "终点经度", required = true, paramType = "query"),
			@ApiImplicitParam(name = "destinationY", value = "终点纬度", required = true, paramType = "query"),
			@ApiImplicitParam(name = "mode", value = "导航模式", required = true, paramType = "query"),
			@ApiImplicitParam(name = "clrOrgNo", value = "清机中心机构编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "tactic", value = "导航策略", required = true, paramType = "query")
	})
	DTO directionRoute(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/group/route")
	@ApiOperation(value = "查询加钞计划分组下的线路", notes = "查询加钞计划分组下的线路")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addnotesPlanNo", value = "计划编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "groupNo", value = "分组编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "tactic", value = "导航策略", required = true, paramType = "query")
	})
	ObjectDTO qryPlanGroupRoute(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX + "/group/tsp")
	@ApiOperation(value = "设备分组并规划线路", notes = "设备分组并规划线路")
	DTO autoGroupTsp(@RequestBody Map<String, Object> paramMap);

	@PutMapping(PREFIX + "/group/tsp")
	@ApiOperation(value = "分组线路信息修改", notes = "分组线路信息修改")
	DTO modGroupTsp(@RequestBody Map<String, Object> paramMap);



	@GetMapping(PREFIX)
	@ApiOperation(value = "查询线路", notes = "查询线路")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "lineName", value = "线路名称", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query"),
			@ApiImplicitParam(name = "lineType", value = "线路类型", paramType = "query")
	})
	PageDTO<LineTableDTO> qryAddnotesLineByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX)
	@ApiOperation(value = "添加线路", notes = "添加线路")
	DTO addAddnotesLine(@RequestBody LineTableDTO lineTableDTO);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改线路", notes = "修改线路")
	DTO modAddnotesLine(@RequestBody LineTableDTO lineTableDTO);

	@GetMapping(PREFIX + "/{lineNo}")
	@ApiOperation(value = "查询线路详情", notes = "查询线路详情")
	@ApiImplicitParam(name = "lineNo", value = "线路编号", required = true, paramType = "path")
	ObjectDTO qrydetailAddnotesLine(@PathVariable("lineNo") String lineNo);

	@DeleteMapping(PREFIX + "/{lineNo}")
	@ApiOperation(value = "删除线路", notes = "删除线路")
	@ApiImplicitParam(name = "lineNo", value = "线路编号", required = true, paramType = "path")
	DTO delAddnotesLine(@PathVariable("lineNo") String lineNo);

	@GetMapping(PREFIX + "/dateAndClrCenter")
	@ApiOperation(value = "根据日期、清分中心查询所有线路", notes = "根据日期、清分中心查询所有线路")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "lineDate", value = "加钞日期[yyyy-MM-dd]", required = true, paramType = "query")
	})
	ListDTO<LineTableDTO> qryLineListByDateAndClrNo(@ApiIgnore @RequestParam Map<String, Object> paramMap);

}
