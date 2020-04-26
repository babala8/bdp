package com.zjft.microservice.treasurybrain.channelcenter.web;


import com.zjft.microservice.treasurybrain.channelcenter.dto.CountTaskInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 崔耀中
 * @since 2019/10/10
 */
@Api(tags = {"渠道中心：清分机信息管理"})
public interface DevCountResource {

	String PREFIX = "${channel-center:}/v2/dev/count";

	@GetMapping(PREFIX)
	@ApiOperation(value = "清分机查询列表", notes = "清分机查询列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "devType",value = "设备类型",example = "1",paramType = "query"),
			@ApiImplicitParam(name = "devNo",value = "设备编号",example = "10001",paramType = "query"),
			@ApiImplicitParam(name = "clrCenterNo",value = "清机中心编号",example = "028001",paramType = "query"),
			@ApiImplicitParam(name = "devStatus",value = "设备状态",example = "1",paramType = "query"),
			@ApiImplicitParam(name = "devModel",value = "设备型号",example = "A1",paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")

	})
	PageDTO<DevCountDTO> queryDevCountList(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@PostMapping(PREFIX)
	@ApiOperation(value = "增加设备",notes = "增加设备信息")
	DTO addDevCountInfo(@RequestBody @ApiParam(name = "设备基本信息", value = "设备基本信息", required = true) DevCountDTO devCountDTO);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改设备信息", notes = "修改设备信息")
	DTO modDevCountInfo(@RequestBody @ApiParam(name = "设备信息", value = "设备信息", required = true) DevCountDTO devCountDTO);

	@DeleteMapping(PREFIX + "/{devNo}")
	@ApiOperation(value = "删除设备信息", notes = "删除设备信息")
	@ApiImplicitParam(name = "devNo", value = "设备编号", example = "10001", required = true, paramType = "path")
	DTO delDevCountInfoByNo(@PathVariable("devNo") String devNo);


	/**
	 * 查询清分机监控状态
	 *
	 * @param paramMap 查询参数
	 * @return 清分机监控状态列表
	 */
	@GetMapping(PREFIX+"/devConMonitoring" )
	@ApiOperation(value = "查询清分机监控状态", notes = "查询清分机监控状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "devNo", value = "设备编号" , paramType = "query"),
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号" ,paramType = "query")
	})
	ListDTO<DevCountDTO> qryDevConMonitoring(@RequestParam @ApiIgnore Map<String, Object> paramMap);

	@GetMapping(PREFIX+"/qryCountTaskNum")
	@ApiOperation(value = "查询清分机正在执行情况", notes = "查询清分机正在执行情况")
	ListDTO<DevCountDTO> qryCountTaskNum();

	@PostMapping(PREFIX+"/allotCount")
	@ApiOperation(value = "分配清分机",notes = "分配清分机")
	@ApiOperationSupport(ignoreParameters = {"countTaskInfoDTO.taskNo","countTaskInfoDTO.countEndDate","countTaskInfoDTO.countAmount"})
	DTO allotCount(@RequestBody CountTaskInfoDTO countTaskInfoDTO);

	@GetMapping(PREFIX + "/qryCountDevList")
	@ApiOperation(value = "查询配钞清分机", notes = "查询配钞清分机")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "devType", value = "清分机种类", paramType = "query")
	})
	ListDTO<DevCountDTO> qryCountDevList(@ApiIgnore @RequestParam Map<String, Object> paramMap);

}
