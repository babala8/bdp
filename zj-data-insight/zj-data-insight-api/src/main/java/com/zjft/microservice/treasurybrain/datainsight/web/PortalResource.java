package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 假接口用于演示
 * @author 杨光
 * @since 2019-04-22
 */
@Api(value = "数据洞察：大屏模块", tags = {"数据洞察：大屏模块"})
public interface PortalResource {

	String PREFIX = "${data-insight:}/v2/portal";

	@GetMapping(PREFIX + "/transAmt")
	@ApiOperation(value = "查询网点现金收付量", notes = "查询网点现金收付量")
	ListDTO qryAllOrgTransAmt();


	@GetMapping(PREFIX + "/transAmtRank")
	@ApiOperation(value = "查询设备现金收付量排名", notes = "查询设备现金收付量排名")
	ListDTO qryAllOrgTransAmtRanking();

}
