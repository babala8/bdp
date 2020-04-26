package com.zjft.microservice.treasurybrain.datainsight.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.datainsight.dto.ChartDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 杨光
 */
@Api(value = "数据洞察：图表模块", tags = {"数据洞察：图表模块"})
public interface ChartResource {

	String PREFIX = "${data-insight:}/v2/chart";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询组件列表", notes = "查询组件列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "chartCreator_Query", value = "创建者", paramType = "query"),
			@ApiImplicitParam(name = "chartName_Query", value = "报表名称", paramType = "query"),
			@ApiImplicitParam(name = "chartOrgNo_Query", value = "机构", paramType = "query"),
			@ApiImplicitParam(name = "chartSubject_Query", value = "主题", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, paramType = "query"),
	})
	PageDTO<ChartDTO> qryChart(@ApiIgnore @RequestParam Map<String, Object> params);

	@GetMapping(value = PREFIX + "/creator")
	@ApiOperation(value = "查询组件创建者列表", notes = "查询组件创建者列表")
	ListDTO queryModelCreators();

	@DeleteMapping(value = PREFIX + "/{id}")
	@ApiOperation(value = "删除图表", notes = "删除图表")
	@ApiImplicitParam(name = "id", value = "id", paramType = "path")
	DTO delChart(@PathVariable(value = "id") String id);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改图表", notes = "修改图表")
	@ApiImplicitParam(name = "params", value = "params", required = true, paramType = "body")
	DTO modModel(@ApiIgnore @RequestBody Map<String, Object> params);

	@GetMapping( PREFIX + "/{id}")
	@ApiOperation(value = "获取图表信息", notes = "获取图表信息")
	@ApiImplicitParam(name = "id", value = "id", paramType = "path")
	ObjectDTO getSelfDataById(@PathVariable(value = "id") String id);
}
