package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDTO;
import com.zjft.microservice.treasurybrain.business.dto.DevClrTimeParamListDTO;
import com.zjft.microservice.treasurybrain.business.dto.DevForChooseDTO;
import com.zjft.microservice.treasurybrain.common.dto.*;
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
@Api(value = "业务中心：加钞计划管理", tags = {"业务中心：加钞计划管理"})
public interface AddnotesPlanResource {

	String PREFIX = "${business:}/v2";


	/**
	 * FIXME @ApiImplicitParam 和 Map<String, Object> paramMap 不一致
	 */
	@PostMapping(PREFIX + "/audit")
	@ApiOperation(value = "添加加钞计划审核", notes = "添加加钞计划审核")
	@ApiImplicitParam(name = "addnotesPlanNo", value = "加钞计划编号", paramType = "query")
	DTO addAddnotesAudit(@RequestBody Map<String, Object> paramMap);

	@PutMapping(PREFIX + "/cash")
	@ApiOperation(value = "修改加钞金额（金额调整）", notes = "修改加钞金额（金额调整）")
	DTO modAddnotesPlanAmts(@RequestBody Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/cash/forecast")
	@ApiOperation(value = "设备金额预测", notes = "设备金额预测")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addnotesPlanNo", value = "计划编号", required = true, paramType = "query")
	})
	DTO addnotesPlanCashReqAmt(@ApiIgnore @RequestParam Map<String, Object> paramMap);

//	@GetMapping(PREFIX + "/group/{addnotesPlanNo}")
//	@ApiOperation(value = "计划分组信息查询", notes = "计划分组信息查询")
//	@ApiImplicitParam(name = "addnotesPlanNo", value = "加钞计划编号", required = true, paramType = "path")
//	ObjectDTO qryGroupTsp(@PathVariable("addnotesPlanNo") String addnotesPlanNo);

	@GetMapping(PREFIX + "/dev/maintain")
	@ApiOperation(value = "查询维护型设备列表", notes = "查询维护型设备列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addnotesPlanNo", value = "加钞计划编号", required = true, paramType = "query")
	})
	ListDTO<DevForChooseDTO> qryAddnotesPlanDevsForMaintain(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/dev/must")
	@ApiOperation(value = "查询决定型设备列表", notes = "查询决定型设备列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addnotesPlanNo", value = "加钞计划编号", required = true, paramType = "query")
	})
	ListDTO<DevForChooseDTO> qryAddnotesPlanDevsForKey(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/dev/predict")
	@ApiOperation(value = "查询预测型设备列表", notes = "查询预测型设备列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addnotesPlanNo", value = "加钞计划编号", required = true, paramType = "query")
	})
	ListDTO<DevForChooseDTO> qryAddnotesPlanDevsForPredict(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/dev/runPlan")
	@ApiOperation(value = "查询计划型设备列表", notes = "查询计划型设备列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addnotesPlanNo", value = "加钞计划编号", required = true, paramType = "query")
	})
	ListDTO<DevForChooseDTO> qryAddnotesPlanDevsForRunPlan(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX + "/dev")
	@ApiOperation(value = "保存加钞设备", notes = "保存加钞设备")
	DTO addAddnotesPlanDevs(@RequestBody Map<String, Object> paramMap);

	@PutMapping(PREFIX + "/dev")
	@ApiOperation(value = "修改加钞设备（设备调整）", notes = "修改加钞设备（设备调整）")
	DTO modAddnotesPlanDevs(@RequestBody Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/dev/addnotesDevAnalyse")
	@ApiOperation(value = "加钞设备分析", notes = "加钞设备分析")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "devNo", value = "设备编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "addnotesDate", value = "加钞日期", required = true, paramType = "query"),
			@ApiImplicitParam(name = "useDays", value = "使用天数", required = true, paramType = "query"),
			@ApiImplicitParam(name = "addnotesAmount", value = "加钞金额", required = true, paramType = "query")
	})
	ObjectDTO getAddnotesDevAnalyse(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PutMapping(PREFIX + "/group")
	@ApiOperation(value = "设备分组信息修改", notes = "设备分组信息修改")
	DTO modGroup(@RequestBody Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/group/detail")
	@ApiOperation(value = "设备分组详细信息查询", notes = "设备分组详细信息查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addnotesPlanNo", value = "计划编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "groupNo", value = "分组编号", required = true, paramType = "query")
	})
	ObjectDTO qryGroupByNo(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PutMapping(PREFIX + "/modDevClrTime")
	@ApiOperation(value = "修改设备加钞周期", notes = "修改设备加钞周期")
	DTO modDevClrTime(@RequestBody DevClrTimeParamListDTO dto);

	@GetMapping(PREFIX + "/plan")
	@ApiOperation(value = "查询加钞计划", notes = "查询加钞计划")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "planStartDate", value = "计划开始日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "planEndDate", value = "计划结束日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "计划状态", paramType = "query"),
			@ApiImplicitParam(name = "lineNo", value = "加钞线路", paramType = "query"),
			@ApiImplicitParam(name = "urgencyFlag", value = "加钞状态", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
	})
	PageDTO<AddnotesPlanDTO> qryAddnotesPlan(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX + "/plan")
	@ApiOperation(value = "添加加钞计划", notes = "添加加钞计划")
	ObjectDTO addAddnotesPlan(@RequestBody Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/plan/{addnotesPlanNo}")
	@ApiOperation(value = "查询加钞计划详情", notes = "查询加钞计划详情")
	@ApiImplicitParam(name = "addnotesPlanNo", value = "加钞计划编号", required = true, paramType = "path")
	ObjectDTO qryAddnotesPlanDetail(@PathVariable("addnotesPlanNo") String addnotesPlanNo);

	@DeleteMapping(PREFIX + "/plan/{addnotesPlanNo}")
	@ApiOperation(value = "删除加钞计划", notes = "删除加钞计划")
	@ApiImplicitParam(name = "addnotesPlanNo", value = "清分中心编号", required = true, paramType = "path")
	DTO delAddnotesPlan(@PathVariable("addnotesPlanNo") String addnotesPlanNo);

	@GetMapping(PREFIX + "/planDetail/{addnotesPlanNo}")
	@ApiOperation(value = "查询加钞计划内容", notes = "查询加钞计划内容（for设备调整、金额调整）")
	@ApiImplicitParam(name = "addnotesPlanNo", value = "加钞计划编号", required = true, paramType = "path")
	ObjectDTO qryAddnotesPlanDetailForDev(@PathVariable("addnotesPlanNo") String addnotesPlanNo);

	@PostMapping(PREFIX + "/refuse")
	@ApiOperation(value = "加钞计划退审", notes = "加钞计划退审")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addnotesPlanNo", value = "计划编号", paramType = "query"),
			@ApiImplicitParam(name = "opinion", value = "审核结果", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "退审意见", paramType = "query"),
	})
	DTO addAddnotesRefuse(@RequestBody Map<String, Object> paramMap);

	/**
	 * 加钞计划出单  已改
	 */
	@PostMapping(PREFIX + "/task")
	@ApiOperation(value = "加钞计划出单", notes = "加钞计划出单")
	DTO planToTask(@RequestBody Map<String, Object> paramMap);


}
