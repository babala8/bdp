package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.param.dto.DevRuleExecDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @ClassName SysParamResource
 * @Description 设备加钞特殊规则设置
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
@Api(value = "参数配置中心：设备加钞特殊规则设置", tags = "参数配置中心：设备加钞特殊规则设置")
public interface DevRuleExecResource {

	String PREFIX = "${param-center:}/v2/devRuleExec";

	/**
	*@MethodName: qryDevRuleExec
	*@Description: 特殊加钞规则分页查询
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.PageDTO<com.zjft.microservice.treasurybrain.param.dto.AddnotesRuleExecDTO>
	*@Date: 2019/12/12 11:05
	**/
	@GetMapping(PREFIX)
	@ApiOperation(value = "特殊加钞规则分页查询", notes = "特殊加钞规则分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "devNo", value = "设备号", paramType = "query"),
			@ApiImplicitParam(name = "addnotesRuleId", value = "特殊规则编号", paramType = "query"),
			@ApiImplicitParam(name = "orgNoFilter", value = "所属机构", paramType = "query"),
			@ApiImplicitParam(name = "startDate", value = "开始日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "endDate", value = "结束日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "执行状态", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
	})
	PageDTO<DevRuleExecDTO> qryDevRuleExec(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/**
	*@MethodName: addDevRuleExec
	*@Description: 添加特殊加钞规则执行
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/12 11:07
	**/
	@PostMapping(PREFIX)
	@ApiOperation(value = "添加特殊加钞规则执行", notes = "添加特殊加钞规则执行")
	DTO addDevRuleExec(@RequestBody Map<String, Object> paramMap);

	/**
	*@MethodName: delSpecialRuleExec
	*@Description: 删除执行规则信息
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/12 11:08
	**/
	@DeleteMapping(PREFIX)
	@ApiOperation(value = "删除执行规则信息", notes = "删除执行规则信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "devNo", value = "设备编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "startDate", value = "开始日期[yyyy-MM-dd]", required = true, paramType = "query"),
			@ApiImplicitParam(name = "endDate", value = "结束日期[yyyy-MM-dd]", required = true, paramType = "query")
	})
	DTO delSpecialRuleExec(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/**
	*@MethodName: qryDevRuleExecByKey
	*@Description: 根据key查询对应的设备规则
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.param.dto.DevRuleExecDTO
	*@Date: 2019/12/23 16:13
	**/
	@GetMapping(PREFIX + "/inner/qryDevRuleExecByKey")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	DevRuleExecDTO qryDevRuleExecByKey(@RequestParam Map<String, Object> paramMap);

	/**
	*@MethodName: modDevRuleExecByKey
	*@Description:
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/23 16:13
	**/
	@PostMapping(PREFIX + "/inner/modDevRuleExecByKey")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	DTO modDevRuleExecByKey(@RequestParam Map<String, Object> paramMap);

}
