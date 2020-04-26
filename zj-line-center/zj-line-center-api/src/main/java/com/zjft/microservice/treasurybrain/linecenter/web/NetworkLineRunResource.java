package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetworkLineRunInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


/**
 * @author zhangjs
 * @since 2019/8/28 18:51
 */
@Api(value = "线路中心：网点线路运行图", tags = "线路中心：网点线路运行图")
public interface NetworkLineRunResource {

	String PREFIX = "${line-center:}/v2/networkLineRunMap";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询网点线路运行图", notes = "查询网点线路运行图")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "networkLineNo", value = "线路编号", paramType = "query"),
			@ApiImplicitParam(name = "startMonth", value = "开始月份[yyyy-mm]", paramType = "query"),
			@ApiImplicitParam(name = "endMonth", value = "结束月份[yyyy-mm]", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
	})
	PageDTO<NetworkLineRunInfoDTO> qryNetworkLineRunMap(@ApiIgnore @RequestParam Map<String, Object> paramMap);


	@PostMapping(PREFIX)
	@ApiOperation(value = "添加网点线路运行图", notes = "添加网点线路运行图")
	DTO addNetworkLineRunMap(@RequestBody LineWorkTableDTO lineWorkTableDTO);


	@PutMapping(PREFIX)
	@ApiOperation(value = "修改网点线路运行图", notes = "修改网点线路运行图")
	DTO modNetworkLineRunMap(@RequestBody LineWorkTableDTO lineWorkTableDTO);


	@GetMapping(PREFIX + "/detail")
	@ApiOperation(value = "查询网点线路运行图详情", notes = "查询网点线路运行图详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "networkLineNo", value = "编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "theYearMonth", value = "年月[yyyy-mm]", required = true, paramType = "query")
	})
	ObjectDTO qrydetailNetworkLineRunMap(@ApiIgnore @RequestParam Map<String, Object> paramMap);

}
