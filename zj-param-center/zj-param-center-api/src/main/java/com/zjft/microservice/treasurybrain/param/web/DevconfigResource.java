package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.param.dto.DevRuleExecDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName DevconfigResource
 * @Description 设备选择参数配置
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
@Api(value = "参数配置中心：设备选择参数配置", tags = "参数配置中心，设备选择参数配置。")
public interface DevconfigResource {

	String PREFIX = "${param-center:}/v2/devConfig";

	/**
	*@MethodName: getDevconfig
	*@Description: 查询设备参数
	*@Author: zhangjs
	*@Param: [clrCenterNo]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.ObjectDTO
	*@Date: 2019/12/12 16:14
	**/
	@GetMapping(PREFIX + "/{clrCenterNo}")
	@ApiOperation(value = "查询设备参数", notes = "查询设备参数")
	@ApiImplicitParam(name = "clrCenterNo", value = "清分中心编号", required = true, paramType = "path")
	ObjectDTO getDevconfig(@PathVariable("clrCenterNo") String clrCenterNo);

	/**
	*@MethodName: devConfig
	*@Description: 修改设备参数
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/12 16:14
	**/
	@PutMapping(PREFIX)
	@ApiOperation(value = "修改设备参数", notes = "修改设备参数")
	DTO devConfig(@RequestBody Map<String, Object> paramMap);

	/**
	*@MethodName: qryIsValidCountsByClrNo
	*@Description: 根据金库编号查询设备规则
	*@Author: zhangjs
	*@Param: [clrCenterNo]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.ObjectDTO
	*@Date: 2019/12/31 17:12
	**/
	@GetMapping(PREFIX + "/inner/qryIsValidCountsByClrNo")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	ObjectDTO qryIsValidCountsByClrNo(@RequestParam String clrCenterNo);

}
