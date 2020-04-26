package com.zjft.microservice.treasurybrain.securitycenter.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.securitycenter.dto.SecurityMessageResponseDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * @author 韩通
 * @since 2020-01-09
 */
@Api(value = "安防中心：预警信息（工作流）", tags = "安防中心：预警信息（工作流）")
public interface SecurityWarnFlowResource {

	String PREFIX = "${security-center:}/v2/securityWarnFlow";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询安防预警信息", notes = "查询安防预警信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warnType", value = "预警类型", paramType = "query"),
			@ApiImplicitParam(name = "startDate", value = "开始月份[yyyy-mm-dd]", paramType = "query"),
			@ApiImplicitParam(name = "endDate", value = "结束月份[yyyy-mm-dd]", paramType = "query"),
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
	})
	@ZjWorkFlow("qrySecurityWarnInfo")
	PageDTO<SecurityMessageResponseDTO> qrySecurityWarnInfo(@ApiIgnore @RequestParam HashMap<String, Object> paramMap);


	@PostMapping(PREFIX)
	@ApiOperation(value = "发送安防预警信息", notes = "发送安防预警信息")
	@DynamicParameters(name = "SecurityMessageMap",properties = {
			@DynamicParameter(name = "warnType",value = "安防预警类别"),
			@DynamicParameter(name = "warnMessage",value = "安防预警信息"),
	})
	@ZjWorkFlow("sendSecurityMessage")
	DTO sendSecurityMessage(@RequestBody HashMap<String, Object> paramMap);
}
