package com.zjft.microservice.treasurybrain.channelcenter.web;


import com.zjft.microservice.treasurybrain.channelcenter.dto.DevBaseInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevClrTimeParamListDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import com.zjft.microservice.treasurybrain.common.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;


/**
 * @author 徐全发
 * @author 杨光
 * @since 2019-03-20
 */
@Api(value = "渠道中心：设备信息管理", tags = {"渠道中心：设备信息管理"})
public interface DevBaseInfoResource {

	String PREFIX = "${channel-center:}/v2/dev";

	@GetMapping(PREFIX + "/clrCenter")
	@ApiOperation(value = "查询清机中心下的所有设备", notes = "查询清机中心下的所有设备")
	@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", required = true, paramType = "query")
	ListDTO<DevBaseInfoDTO> qryDevInfoByClrNo(@RequestParam("clrCenterNo") String clrCenterNo);

	@GetMapping(PREFIX )
	@ApiOperation(value = "查询设备信息分页列表", notes = "查询设备信息分页列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "no", value = "设备编号", paramType = "query"),
			@ApiImplicitParam(name = "address", value = "设备地址", paramType = "query"),
			@ApiImplicitParam(name = "addnotesLine", value = "加钞线路", paramType = "query"),
			@ApiImplicitParam(name = "ip", value = "ip地址", paramType = "query"),
			@ApiImplicitParam(name = "orgNo", value = "机构编号", paramType = "query"),
			@ApiImplicitParam(name = "clrCenterNo", value = "金库编号", paramType = "query"),
			@ApiImplicitParam(name = "devVendor", value = "设备品牌", paramType = "query"),
			@ApiImplicitParam(name = "devCatalog", value = "设备类型", paramType = "query"),
			@ApiImplicitParam(name = "devType", value = "设备型号", paramType = "query"),
			@ApiImplicitParam(name = "devTypeCash", value = "存取类型", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, paramType = "query"),
	})
	PageDTO<DevBaseInfoDTO> queryDevInfoList(@ApiIgnore @RequestParam Map<String, Object> params);


	@PostMapping(PREFIX )
	@ApiOperation(value = "新增设备信息", notes = "新增设备信息")
	DTO addDevInfo(@RequestBody DevBaseInfoDTO dto);


	@PutMapping(PREFIX )
	@ApiOperation(value = "修改设备信息", notes = "修改设备信息")
	DTO modDevInfo(@RequestBody DevBaseInfoDTO dto);


	@DeleteMapping(PREFIX +"/{no}")
	@ApiOperation(value = "删除设备信息", notes = "删除设备信息")
	@ApiImplicitParam(name = "no", value = "参数no", required = true, paramType = "path")
	DTO delDevInfoByNo(@PathVariable("no") String no);


	@GetMapping(PREFIX + "/catalog")
	@ApiOperation(value = "查询设备类型列表", notes = "查询设备类型列表")
	ListDTO<DevCatalogDTO> qryDevCatalog();

	@GetMapping(PREFIX + "/qryDev")
	@ApiOperation(value = "查询设备信息及使用信息", notes = "查询设备信息及使用信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "devNo", value = "设备编号", required = true, paramType = "query")
	})
	ObjectDTO getDevBaseInfoByNo(@ApiIgnore @RequestParam Map<String, Object> paramMap);


	/**
	 *
	 * 查询单台设备的清机周期信息
	 *
	 * @param devNo 设备编号
	 * @return
	 */
	@GetMapping(PREFIX+"/{devNo}")
	@ApiOperation(value = "查询单台设备的清机周期信息",notes = "查询单台设备的清机周期信息")
	DevClrTimeParamListDTO qryByDevNo(@PathVariable(name = "devNo")  String devNo);

	@GetMapping(PREFIX + "/devsInfo")
	@ApiOperation(value = "根据设备号查询设备信息", notes = "根据设备号查询设备信息")
	@ApiImplicitParam(name = "atmCode", value = "atm编号", required = true, paramType = "query")
	ListDTO<List> qryDevsInfoByOrgNo(@RequestParam String atmCode);


}

