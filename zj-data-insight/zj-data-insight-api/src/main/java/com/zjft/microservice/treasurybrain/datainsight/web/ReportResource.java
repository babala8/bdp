package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 杨光
 */
@Api(value = "数据洞察：报表模块", tags = {"数据洞察：报表模块"})
public interface ReportResource {

	String PREFIX = "${data-insight:}/v2/report";

	/**
	 * 用户机构下报表模板列表
	 *
	 * @return ListDTO
	 */
	@GetMapping(value = PREFIX + "/model")
	@ApiOperation(value = "获取用户机构下所有报表列表", notes = "获取用户机构下所有报表列表")
	ListDTO getReports();


	@PostMapping(value = PREFIX + "/params")
	@ApiOperation(value = "查询报表", notes = "查询报表")
	String qry(HttpServletRequest request, HttpServletResponse response,
			   @RequestBody Map<String, Object> params) throws ServletException, IOException;


	@GetMapping(value = PREFIX + "/property")
	@ApiOperation(value = "查询配置项信息", notes = "查询配置项信息")
	@ApiImplicitParam(name = "reportName", value = "报表文件名称", required = true, paramType = "query")
	ListDTO getProperties(@ApiIgnore @RequestParam Map<String, Object> params);


	@PostMapping(value = PREFIX + "/exp")
	@ApiOperation(value = "导出报表", notes = "导出报表")
	String export(@ApiIgnore HttpServletRequest request,
				  @ApiIgnore HttpServletResponse response,
				  @RequestBody Map<String, Object> params) throws ServletException, IOException;


	@GetMapping(value = PREFIX + "/page")
	@ApiOperation(value = "报表列表分页查询", notes = "报表列表分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "reportName", value = "报表名称", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, paramType = "query"),
	})
	PageDTO queryReportsByPage(@ApiIgnore @RequestParam Map<String, Object> params);


	@DeleteMapping(value = PREFIX + "/{no}")
	@ApiImplicitParam(name = "no", value = "no", paramType = "path")
	@ApiOperation(value = "删除报表", notes = "删除报表")
	DTO delReport(@PathVariable(value = "no") String no);


	@PostMapping(PREFIX)
	@ApiOperation(value = "添加报表", notes = "添加报表")
	DTO addReport(@RequestBody Map<String, Object> params);


	@GetMapping(PREFIX + "/fileDetail")
	@ApiOperation(value = "查看报表文件详情", notes = "查看报表文件详情")
	@ApiImplicitParam(name = "orgNo", value = "报表名称", required = true, paramType = "query")
	ObjectDTO getFileDetailByFileName(@ApiIgnore @RequestParam Map<String, Object> params);


	@GetMapping(value = PREFIX + "/group")
	@ApiOperation(value = "查询报表所有分组", notes = "查询报表所有分组")
	ListDTO getGroups();
}
