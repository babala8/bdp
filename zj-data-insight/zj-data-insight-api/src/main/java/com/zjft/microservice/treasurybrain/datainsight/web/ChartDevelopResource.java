package com.zjft.microservice.treasurybrain.datainsight.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 杨光
 */
@Api(value = "数据洞察：开发者模式", tags = {"数据洞察：开发者模式"})
public interface ChartDevelopResource {

	String PREFIX = "${data-insight:}/v2/chartsDevelop";

	/**
	 * 组件预览，获取数据
	 *
	 * @param jsonParams serviceURL,serviceMethod,chartType,dimensionRows,measureRows
	 * @return ObjectDTO
	 */
	@PostMapping(value = PREFIX + "/preview")
	@ApiOperation(value = "组件预览", notes = "组件预览")
	@ApiImplicitParam(name = "jsonParams", value = "jsonParams", required = true, paramType = "body")
	ObjectDTO preview(@ApiIgnore @RequestBody String jsonParams);

	/**
	 * 组件保存
	 *
	 * @param params componentMsg,modelMsg,optionMsg
	 * @return BaseDTO
	 */
	@PostMapping(PREFIX)
	@ApiOperation(value = "组件保存", notes = "组件保存")
	@ApiImplicitParam(name = "jsonParams", value = "jsonParams", required = true, paramType = "body")
	DTO addChartsModel(@ApiIgnore @RequestBody Map<String, Object> params);

	/**
	 * 根据用户机构号查询组件列表
	 *
	 * @return BaseDTO
	 */
	@GetMapping(PREFIX)
	@ApiOperation(value = "根据用户机构号查询组件列表", notes = "根据用户机构号查询组件列表")
	DTO qryChartsModel();

	/**
	 * 查询组件主题列表
	 *
	 * @return BaseDTO
	 */
	@GetMapping(value = PREFIX + "/subjects")
	@ApiOperation(value = "查询组件主题列表", notes = "查询组件主题列表")
	DTO qryChartSubjects();


	/**
	 * 查询数据服务列表
	 *
	 * @return BaseDTO
	 */
	@GetMapping(value = PREFIX + "/serviceList")
	@ApiOperation(value = "查询数据服务列表", notes = "查询数据服务列表")
	DTO qryServiceList();
}
