package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api(value = "数据洞察：明细查询模块", tags = {"数据洞察：明细查询模块"})
public interface DetailQueryManageResource {

	String PREFIX = "${data-insight:}/v2/detailQuery";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询明细查询信息", notes = "查询明细查询信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "名称", paramType = "query"),
			@ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, paramType = "query"),
	})
	PageDTO qryDetailQuery(@ApiIgnore @RequestParam Map<String, Object> params);

	@DeleteMapping(PREFIX + "/{no}")
	@ApiOperation(value = "删除明细查询", notes = "删除明细查询")
	@ApiImplicitParam(name = "no", value = "no", paramType = "path")
	DTO delQuery(@PathVariable(value = "no") String no);
}
