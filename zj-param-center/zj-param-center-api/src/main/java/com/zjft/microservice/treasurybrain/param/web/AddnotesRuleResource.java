package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.param.dto.AddnotesRuleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @ClassName AddnotesRuleResource
 * @Description 加钞特殊规则设置
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
@Api(value = "参数配置中心：加钞特殊规则管理", tags = "参数配置中心：加钞特殊规则管理")
public interface AddnotesRuleResource {

	String PREFIX = "${param-center:}/v2/rule";

	/**
	*@MethodName: qryAddnotesRule
	*@Description: 查询特殊加钞规则
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.PageDTO<com.zjft.microservice.treasurybrain.param.dto.AddnotesRuleDTO>
	*@Date: 2019/12/12 11:17
	**/
	@GetMapping(PREFIX)
	@ApiOperation(value = "查询特殊规则", notes = "查询特殊规则")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "ruleId", value = "规则编号", paramType = "query"),
			@ApiImplicitParam(name = "ruleGenOpName", value = "规则制订人", paramType = "query"),
			@ApiImplicitParam(name = "startDate", value = "开始日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "endDate", value = "结束日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query"),
	})
	PageDTO<AddnotesRuleDTO> qryAddnotesRule(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/**
	*@MethodName: addAddnotesRule
	*@Description: 增加加钞特殊规则
	*@Author: zhangjs
	*@Param: [addnotesRuleDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/12 11:19
	**/
	@PostMapping(PREFIX)
	@ApiOperation(value = "添加特殊规则", notes = "添加特殊规则")
	DTO addAddnotesRule(@RequestBody AddnotesRuleDTO addnotesRuleDTO);

	/**
	*@MethodName: modAddnotesRule
	*@Description: 修改加钞特殊规则
	*@Author: zhangjs
	*@Param: [addnotesRuleDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/12 11:19
	**/
	@PutMapping(PREFIX)
	@ApiOperation(value = "修改特殊规则", notes = "修改特殊规则")
	DTO modAddnotesRule(@RequestBody AddnotesRuleDTO addnotesRuleDTO);

	/**
	*@MethodName: detailAddnotesRule
	*@Description: 查询加钞规则详情
	*@Author: zhangjs
	*@Param: [ruleId]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.ObjectDTO
	*@Date: 2019/12/12 11:33
	**/
	@GetMapping(PREFIX + "/{ruleId}")
	@ApiOperation(value = "查询特殊规则详情", notes = "查询特殊规则详情")
	@ApiImplicitParam(name = "ruleId", value = "规则编号", required = true, paramType = "path")
	ObjectDTO detailAddnotesRule(@PathVariable("ruleId") String ruleId);

	/**
	*@MethodName: delAddnotesRule
	*@Description:
	*@Author: zhangjs
	*@Param: [ruleId]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/12 11:34
	**/
	@DeleteMapping(PREFIX + "/{ruleId}")
	@ApiOperation(value = "删除特殊规则", notes = "删除特殊规则")
	@ApiImplicitParam(name = "ruleId", value = "规则编号", required = true, paramType = "path")
	DTO delAddnotesRule(@PathVariable("ruleId") String ruleId);

}
