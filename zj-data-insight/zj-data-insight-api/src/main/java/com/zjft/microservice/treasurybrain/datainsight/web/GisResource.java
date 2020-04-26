package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.datainsight.dto.GisPointInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 杨光
 */
@Api(value = "数据洞察：GIS模块", tags = {"数据洞察：GIS模块"})
public interface GisResource {

	String PREFIX = "${data-insight:}/v2/gis/template";

	/**
	 * 添加或更新网点模板
	 *
	 * @param params orgNo, status，jsTemplate，htmlTemplate
	 * @return DTO
	 */
	@PostMapping(PREFIX)
	@ApiOperation(value = "添加或更新网点模板", notes = "添加或更新网点模板")
	DTO addTemplate(@RequestBody Map<String, Object> params);

	/**
	 * 查询模板
	 *
	 * @param orgNo orgNo
	 * @return GisPointInfoDTO
	 */
	@GetMapping(PREFIX)
	@ApiOperation(value = "查询网点模板", notes = "查询网点模板")
	@ApiImplicitParam(name = "orgNo", value = "机构号", required = true, paramType = "query")
	GisPointInfoDTO qryTemplate(@ApiIgnore @RequestParam String orgNo);
}
