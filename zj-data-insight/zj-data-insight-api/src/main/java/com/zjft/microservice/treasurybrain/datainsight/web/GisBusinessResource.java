package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;


/**
 * @author 杨光
 */
@Api(value = "数据洞察：GIS模块", tags = {"数据洞察：GIS模块"})
public interface GisBusinessResource {

	String PREFIX = "${data-insight:}/v2/gis/business";

	@GetMapping(PREFIX + "/pointInMap")
	@ApiOperation(value = "地图中查询网点", notes = "地图中查询网点")
	ListDTO qryPointInMap();

	@GetMapping(PREFIX + "/points")
	@ApiOperation(value = "根据城市查询网点列表", notes = "根据城市查询网点列表")
	@ApiImplicitParam(name = "cityName", value = "城市名称", required = true, paramType = "query")
	ListDTO qryPointsByCity(@RequestParam String cityName);

	@GetMapping(PREFIX + "/ranking")
	@ApiOperation(value = "根据指定信息查询排名", notes = "根据指定信息查询排名")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cityName", value = "城市名称", paramType = "query"),
			@ApiImplicitParam(name = "index", value = "顺、逆序，默认为顺序", paramType = "query"),
			@ApiImplicitParam(name = "topN", value = "行数限制、必输", paramType = "query"),
			@ApiImplicitParam(name = "orgNo", value = "机构编号", paramType = "query"),
			@ApiImplicitParam(name = "subject", value = "subject", paramType = "query"),
	})
	ListDTO qryRanking(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/caseList")
	@ApiOperation(value = "根据设备号查询CASE", notes = "根据设备号查询CASE")
	@ApiImplicitParam(name = "atmCode", value = "atm编号", required = true, paramType = "query")
	ListDTO<List> qryCaseListByAtm(@RequestParam String atmCode);

	@GetMapping(PREFIX + "/caseTypes")
	@ApiOperation(value = "根据设备号查询CASE类型", notes = "根据设备号查询CASE类型")
	@ApiImplicitParam(name = "atmCode", value = "atm编号", required = true, paramType = "query")
	ListDTO<List> qryCaseTypesByAtm(@RequestParam String atmCode);

	@GetMapping(PREFIX + "/devsInfo")
	@ApiOperation(value = "根据设备号查询设备信息", notes = "根据设备号查询设备信息")
	@ApiImplicitParam(name = "atmCode", value = "atm编号", required = true, paramType = "query")
	ListDTO<List> qryDevsInfoByOrgNo(@RequestParam String atmCode);

	/*@RequestMapping(method = RequestMethod.GET, value = "/devsInfo")
	@ApiOperation(value = "根据设备号查询设备信息", notes = "根据设备号查询设备信息")
	ListDTO<List> qryDevsInfoByOrgNo(@RequestParam Map<String, Object> params);*/

	/*@GetMapping(PREFIX + "/ranking")
	@ApiOperation(value = "根据指定信息查询排名", notes = "根据指定信息查询排名")
	ListDTO qryRanking(@RequestParam Map<String, Object> paramMap);*/

/*	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ObjectDTO invokeService(@RequestParam Map<String, Object> paramMap, @PathVariable(value = "id") String sid);*/

}
