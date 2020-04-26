package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 杨光
 */
@Api(value = "数据洞察：明细查询模块", tags = {"数据洞察：明细查询模块"})
public interface DetailQueryResource {

	String PREFIX = "${data-insight:}/v2/detailDevelop";

	@PostMapping(PREFIX + "/detail")
	@ApiOperation(value = "查询明细信息", notes = "查询明细信息")
	ObjectDTO qry(@RequestBody Map<String, Object> params);


	@PostMapping(value = PREFIX + "/export")
	@ApiOperation(value = "导出明细查询", notes = "导出明细查询")
	String export(HttpServletRequest request,
				  HttpServletResponse response,
				  @RequestBody Map<String, Object> params);


	@PostMapping(PREFIX + "/model")
	@ApiOperation(value = "保存明细查询", notes = "保存明细查询")
	DTO saveReport(@RequestBody Map<String, Object> params);


	/**
	 * 明细查询详情
	 */
	@GetMapping(PREFIX + "/model/{id}")
	@ApiImplicitParam(name = "id", value = "id", paramType = "path")
	@ApiOperation(value = "由明细查询ID查询信息", notes = "由明细查询ID查询信息")
	ObjectDTO qrySelfDefDetail(@PathVariable(value = "id") String id);

	/**
	 * 当前用户机构下所有明细查询列表
	 */
	@GetMapping(PREFIX + "/model")
	@ApiOperation(value = "当前用户机构下所有明细查询列表", notes = "当前用户机构下所有明细查询列表")
	ListDTO getDefDetails();


	/**
	 * 获取明细查询分组
	 */
	@GetMapping(value = PREFIX + "/groups")
	@ApiOperation(value = "获取明细查询分组", notes = "获取明细查询分组")
	ListDTO getGroups();


	/**
	 * 查询数据服务列表
	 */
	@GetMapping(PREFIX + "/serviceList")
	@ApiOperation(value = "查询数据服务列表", notes = "查询数据服务列表")
	ListDTO qryServiceList();
}
