package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.param.dto.DevRuleExecDTO;
import com.zjft.microservice.treasurybrain.param.dto.SpDateCoeffDTO;
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
/**
 * @ClassName SpDateCoeffResource
 * @Description 加钞特殊日期配钞设置
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
@Api(value = "参数配置中心：加钞特殊日期配钞设置", tags = {"参数配置中心：加钞特殊日期配钞设置"})
public interface SpDateCoeffResource {

	String PREFIX = "${param-center:}/v2/spDateCoeff";

	/**
	*@MethodName: qry
	*@Description: 分页查询加钞特殊日期配钞设置
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.PageDTO<com.zjft.microservice.treasurybrain.param.dto.SpDateCoeffDTO>
	*@Date: 2019/12/12 16:07
	**/
	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询加钞特殊日期配钞设置", notes = "分页查询加钞特殊日期配钞设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "specialDateStart", value = "开始日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "specialDateEnd", value = "结束日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "总页数", paramType = "query"),
	})
	PageDTO<SpDateCoeffDTO> qry(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/**
	*@MethodName: addSpDateCoeff
	*@Description: 新增加钞特殊日期配钞设置
	*@Author: zhangjs
	*@Param: [spDateCoeffDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/12 16:08
	**/
	@PostMapping(PREFIX)
	@ApiOperation(value = "增加加钞特殊日期配钞设置", notes = "增加加钞特殊日期配钞设置")
	DTO addSpDateCoeff(@RequestBody SpDateCoeffDTO spDateCoeffDTO);

	/**
	*@MethodName: modSpDateCoeff
	*@Description: 修改加钞特殊日期配钞设置
	*@Author: zhangjs
	*@Param: [spDateCoeffDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/12 16:09
	**/
	@PutMapping(PREFIX)
	@ApiOperation(value = "修改加钞特殊日期配钞信息", notes = "修改加钞特殊日期配钞信息")
	DTO modSpDateCoeff(@RequestBody SpDateCoeffDTO spDateCoeffDTO);

	/**
	*@MethodName: delRegionAddnotes
	*@Description: 删除加钞特殊日期配钞信息
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/12 16:09
	**/
	@DeleteMapping(PREFIX)
	@ApiOperation(value = "删除加钞特殊日期配钞信息", notes = "删除加钞特殊日期配钞信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "startDate", value = "开始日期[yyyy-MM-dd]", required = true, paramType = "query"),
			@ApiImplicitParam(name = "endDate", value = "结束日期[yyyy-MM-dd]", required = true, paramType = "query"),
	})
	DTO delRegionAddnotes(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/**
	*@MethodName: qrySpDateCoeffByKey
	*@Description: 查询单个加钞特殊日期配钞信息
	*@Author: zhangjs
	*@Param: [paramMap]
	*@Return: com.zjft.microservice.treasurybrain.param.dto.SpDateCoeffDTO
	*@Date: 2019/12/31 11:30
	**/
	@GetMapping(PREFIX + "/inner/qrySpDateCoeffByKey")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	SpDateCoeffDTO qrySpDateCoeffByKey(@RequestParam Map<String, Object> paramMap);

}
