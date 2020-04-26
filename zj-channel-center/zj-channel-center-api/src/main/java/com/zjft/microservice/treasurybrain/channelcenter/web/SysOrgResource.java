package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.OrgBusinessTimeDTO;
import com.zjft.microservice.treasurybrain.common.dto.*;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 */
@Api(value = "渠道中心：机构管理", tags = {"渠道中心：机构管理"})
public interface SysOrgResource {

	String PREFIX = "${channel-center:}/v2/org";

	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询机构信息列表", notes = "分页查询机构信息列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "fuzzyOrgNo", value = "模糊机构编号", paramType = "query"),
			@ApiImplicitParam(name = "orgNo", value = "所属机构编号", paramType = "query"),
			@ApiImplicitParam(name = "orgName", value = "机构名称", paramType = "query"),
			@ApiImplicitParam(name = "orgGrade", value = "机构等级", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, paramType = "query"),
	})
	PageDTO<SysOrgDTO> qrySysOrgByPage(@ApiIgnore @RequestParam Map<String, Object> page);


	@GetMapping(PREFIX + "/grade")
	@ApiOperation(value = "查询所有机构类型", notes = "查询所有机构类型")
	ListDTO<SysOrgGradeDTO> qrySysOrgGrade();


	@PostMapping(PREFIX)
	@ApiOperation(value = "添加机构", notes = "添加机构")
	DTO addSysOrg(@RequestBody SysOrgDTO dto);


	@PutMapping(PREFIX)
	@ApiOperation(value = "修改机构", notes = "修改机构")
	DTO modSysOrgById(@RequestBody SysOrgDTO dto);


	@DeleteMapping(PREFIX + "/{no}")
	@ApiOperation(value = "删除机构", notes = "删除机构")
	@ApiImplicitParam(name = "no", value = "机构编号", required = true, paramType = "path")
	DTO delSysOrgById(@PathVariable("no") String no);


	@GetMapping(PREFIX + "/children")
	@ApiOperation(value = "查询下一级机构列表", notes = "查询下一级机构列表")
	ListDTO qryChildrenOrgByAuth(@RequestParam(value = "parentOrgNo", defaultValue = "") String parentOrgNo);


	@GetMapping(PREFIX + "/{no}")
	@ApiOperation(value = "查询机构详情", notes = "查询机构详情")
	@ApiImplicitParam(name = "no", value = "机构编号", required = true, paramType = "path")
	SysOrgDTO qrySysOrgDetailByNo(@PathVariable("no") String no);


	@GetMapping(PREFIX + "/fuzzy")
	@ApiOperation(value = "模糊查询机构列表", notes = "模糊查询机构列表")
	@ApiImplicitParam(name = "orgName", value = "机构名称", required = true, paramType = "query")
	ListDTO qryOrgFuzzyByAuth(@RequestParam("orgName") String orgName);

	@GetMapping(PREFIX + "/orgBusinessTime/qryOrgBusinessTime")
	@ApiOperation(value = "查询网点营业时间", notes = "查询网点营业时间")
	@ApiImplicitParam(name = "orgNo", value = "机构名称", required = true, paramType = "query")
	ListDTO<OrgBusinessTimeDTO> qryOrgBusinessTime(@RequestParam("orgNo") String orgNo);

	@PutMapping(PREFIX + "/orgBusinessTime/modOrgBusinessTime")
	@ApiOperation(value = "修改网点营业时间", notes = "修改网点营业时间")
	@ApiOperationSupport(ignoreParameters = {"dto.closeTime","dto.openTime","dto.orgDay","dto.orgTimeInterval","dto.retCode","dto.retMsg","dto.tid","dto.businessTimeList.businessTimeList","dto.businessTimeList.orgNo","dto.businessTimeList.retCode","dto.businessTimeList.tid","dto.businessTimeList.retMsg"})
	DTO modOrgBusinessTime(@RequestBody OrgBusinessTimeDTO dto);

	@GetMapping(PREFIX + "/clrCenter/netpoints/{clrCenterNo}")
	@ApiOperation(value = "获取清分中心下所有网点", notes = "获取清分中心下所有网点")
	@ApiImplicitParam(name = "clrCenterNo", value = "清分中心编号", required = true, paramType = "path")
	ListDTO<SysOrgDTO> getNetpointsByClrCenterNo(@PathVariable("clrCenterNo") String clrCenterNo);

	@GetMapping(PREFIX + "/group/netpoints")
	@ApiOperation(value = "查询分组下的网点信息", notes = "查询分组下的网点信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addnotesPlanNo", value = "计划编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "groupNo", value = "分组编号", required = true, paramType = "query")
	})
	ListDTO getNetpointsWithDevsOfGroup(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/pointInMap")
	@ApiOperation(value = "地图中查询网点", notes = "地图中查询网点")
	ListDTO qryPointInMap();

	@GetMapping(PREFIX + "/points")
	@ApiOperation(value = "根据城市查询网点列表", notes = "根据城市查询网点列表")
	@ApiImplicitParam(name = "cityName", value = "城市名称", required = true, paramType = "query")
	ListDTO qryPointsByCity(@RequestParam String cityName);


}
