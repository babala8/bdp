package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.param.dto.SysParamCatalogDTO;
import com.zjft.microservice.treasurybrain.param.dto.SysParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @ClassName SysParamResource
 * @Description 系统参数设置
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
@Api(value = "参数配置中心：系统参数管理", tags = {"参数配置中心：系统参数管理"})
public interface SysParamResource {

	String PREFIX = "${param-center:}/v2/paramManage";

	/**
	*@MethodName: qrySysParamByName
	*@Description: 根据参数名称查询系统参数
	*@Author: zhangjs
	*@Param: [paramName]
	*@Return: com.zjft.microservice.treasurybrain.param.dto.SysParamDTO
	*@Date: 2019/12/11 19:17
	**/
	@GetMapping(PREFIX + "/name")
	@ApiOperation(value = "根据参数名称查询系统参数", notes = "根据参数名称查询系统参数")
	@ApiImplicitParam(name = "paramName", value = "参数名称", required = true, paramType = "query")
	SysParamDTO qrySysParamByName(@RequestParam("paramName") String paramName);

	/**
	*@MethodName: qrySysParamByPage
	*@Description: 分页查询系统参数列表
	*@Author: zhangjs
	*@Param: [page]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.PageDTO<com.zjft.microservice.treasurybrain.param.dto.SysParamDTO>
	*@Date: 2019/12/11 19:18
	**/
	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询系统参数列表", notes = "分页查询系统参数列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "paramName", value = "参数名称", paramType = "query"),
			@ApiImplicitParam(name = "catalog", value = "参数类型", paramType = "query"),
			@ApiImplicitParam(name = "statement", value = "参数说明", paramType = "query"),
			@ApiImplicitParam(name = "description", value = "参数描述", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, paramType = "query"),
	})
	PageDTO<SysParamDTO> qrySysParamByPage(@ApiIgnore @RequestParam Map<String, Object> page);

	/**
	*@MethodName: qrySysParamType
	*@Description:  查询所有参数类型
	*@Author: zhangjs
	*@Param: []
	*@Return: com.zjft.microservice.treasurybrain.common.dto.ListDTO<com.zjft.microservice.treasurybrain.param.dto.SysParamCatalogDTO>
	*@Date: 2019/12/11 19:19
	**/
	@GetMapping(PREFIX + "/type")
	@ApiOperation(value = "查询所有参数类型", notes = "查询所有参数类型")
	ListDTO<SysParamCatalogDTO> qrySysParamType();

	/**
	*@MethodName: addSysParam
	*@Description: 新增系统参数
	*@Author: zhangjs
	*@Param: [sysParamDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/11 19:20
	**/
	@PostMapping(PREFIX)
	@ApiOperation(value = "新增系统参数", notes = "{'paramName':'','paramValue':'','catalog':'','statement':'','description':''}")
	DTO addSysParam(@RequestBody SysParamDTO sysParamDTO);

	/**
	*@MethodName: modSysParamById
	*@Description:  修改系统参数
	*@Author: zhangjs
	*@Param: [sysParamDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/11 19:20
	**/
	@PutMapping(PREFIX)
	@ApiOperation(value = "修改系统参数", notes = "{'logicId':'','paramName':'','paramValue':'','catalog':'','statement':'','description':''}")
	DTO modSysParamById(@RequestBody SysParamDTO sysParamDTO);

	/**
	*@MethodName: delSysParamById
	*@Description:  删除系统参数
	*@Author: zhangjs
	*@Param: [id] 参数id
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/11 19:21
	**/
	@DeleteMapping(PREFIX + "/{id}")
	@ApiOperation(value = "删除系统参数", notes = "删除系统参数")
	@ApiImplicitParam(name = "id", value = "参数id", required = true, paramType = "path")
	DTO delSysParamById(@PathVariable("id") String id);

}

