package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.*;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/24 17:10
 */
@Api(value = "用户中心：人员排班管理",tags = "用户中心：人员排班管理")
public interface ScheduleResource {
	String PREFIX = "${user-center:}/v2/schedule";

	/**
	 *
	 * 分页查询
	 *
	 * @param paramsMap
	 * @return
	 */
	@GetMapping(PREFIX + "/escort")
	@ApiOperation(value = "分页查询押运人员排班信息",notes = "分页查询押运人员排班信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="startDate" ,value = "开始日期",paramType = "query"),
			@ApiImplicitParam(name ="endDate" ,value = "结束日期",paramType = "query"),
			@ApiImplicitParam(name = "clrCenterNo",value = "金库编号",paramType = "query"),
			@ApiImplicitParam(name = "lineNo",value = "线路编号"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")

	})
	PageDTO<OutSourcingLineMapDTO> qryOutSourcingByPage(@RequestParam @ApiIgnore Map<String, Object> paramsMap);


	@PostMapping(PREFIX + "/escort")
	@ApiOperation(value = "覆盖生成押运人员排班信息",notes = "覆盖生成押运人员排班信息")
	DTO addOutSourcing(@RequestBody OutSourcingLineMapAddDTO outSourcingLineMapAddDTO);

	@DeleteMapping(PREFIX+"/escort/{no}")
	@ApiOperation(value = "删除押运人员排班信息",notes = "删除押运人员排班信息")
	DTO deleteOutSourcing(@PathVariable String no);

	@PutMapping(PREFIX + "/escort")
	@ApiOperation(value = "调整押运人员排班信息",notes = "调整押运人员排班信息")
	DTO modOutSourcing(@RequestBody OutSourcingLineMapDTO outSourcingLineMapDTO);

	@PostMapping(PREFIX+"/escort/export")
	@ApiOperation(value = "押运排班表导出")
	DTO export(@ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response,
			   @RequestBody OutSourcingLineMapExportDTO outSourcingLineMapExportDTO);


	@PostMapping(PREFIX + "/postScheduleMould")
	@ApiOperation(value = "新增排班模板", notes = "新增排班模板")
	@ApiOperationSupport(ignoreParameters = {"postScheduleMouldDTO.mouldNo", "postScheduleMouldDTO.orgName", "postScheduleMouldDTO.detailList.classesName", "postScheduleMouldDTO.detailList.opList.opName"})
	DTO addPostScheduleMould(@RequestBody PostScheduleMouldDTO postScheduleMouldDTO);

	@GetMapping(PREFIX + "/postScheduleMould")
	@ApiOperation(value = "分页查询排班模板信息", notes = "分页查询排班模板信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgNo", value = "所属机构"),
			@ApiImplicitParam(name = "postNo", value = "岗位编号"),
			@ApiImplicitParam(name = "mouldType", value = "模板类型"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<PostScheduleMouldDTO> qryPostScheduleMouldBypage(@RequestParam @ApiIgnore Map<String, Object> param);

	@PutMapping(PREFIX + "/postScheduleMould")
	@ApiOperation(value = "修改排班模板信息", notes = "修改排班模板信息")
	@ApiOperationSupport(ignoreParameters = {"postScheduleMouldDTO.orgName", "postScheduleMouldDTO.postName", "postScheduleMouldDTO.detailList.classesName", "postScheduleMouldDTO.detailList.opList.opName"})
	DTO modPostScheduleMould(@RequestBody PostScheduleMouldDTO postScheduleMouldDTO);

	@DeleteMapping(PREFIX + "/postScheduleMould/{mouldNo}")
	@ApiOperation(value = "删除排班模板信息", notes = "删除排班模板信息")
	DTO deletePostScheduleMould(@PathVariable("mouldNo") String mouldNo);

	@GetMapping(PREFIX + "/all")
	@ApiOperation(value = "根据岗位和机构查询下属模板", notes = "根据岗位和机构查询下属模板")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgNo", value = "所属机构"),
			@ApiImplicitParam(name = "postNo", value = "岗位编号")
	})
	ListDTO<PostScheduleMouldDTO> qryAllMouldByOrgNoAndPostNo(@RequestParam @ApiIgnore Map<String, Object> map);


	@PostMapping(PREFIX + "/postSchedule")
	@ApiOperation(value = "生成排班信息", notes = "生成排班信息")
	DTO createSchedule(@RequestBody CreateScheduleDTO createScheduleDTO);

	@PutMapping(PREFIX + "/postSchedule")
	@ApiOperation(value = "修改排班信息", notes = "修改排班信息")
	@ApiOperationSupport(ignoreParameters = {"postScheduleDTO.orgNO","postScheduleDTO.postNo","postScheduleDTO.mouldNo",
			"postScheduleDTO.createTime","postScheduleDTO.detailList.classesName","postScheduleDTO.detailList.opList.opName"})
	DTO modSchedule(@RequestBody PostScheduleDTO postScheduleDTO);

	@GetMapping(PREFIX + "/postSchedule")
	@ApiOperation(value = "分页查询排班信息", notes = "分页查询排班信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgNo", value = "所属机构"),
			@ApiImplicitParam(name = "postNo", value = "岗位编号"),
			@ApiImplicitParam(name = "mouldNo", value = "模板编号"),
			@ApiImplicitParam(name = "scheduleMonth", value = "月份"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<PostScheduleDTO> qryScheduleBypage(@RequestParam @ApiIgnore Map<String, Object> param);

	@DeleteMapping(PREFIX + "/postSchedule/{planNo}")
	@ApiOperation(value = "删除排班计划信息", notes = "删除排班计划信息")
	DTO deleteSchedule(@PathVariable("planNo") String planNo);
}
