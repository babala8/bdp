package com.zjft.microservice.treasurybrain.securitycenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.securitycenter.dto.SecurityMessageResponseDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


/**
 * @author zhangjs
 * @since 2019/9/17 19:53
 */
@Api(value = "安防中心：预警信息", tags = "安防中心：预警信息")
public interface SecurityWarnResource {

	String PREFIX = "${security-center:}/v2/securityWarn";

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
	PageDTO<SecurityMessageResponseDTO> qrySecurityWarnInfo(@ApiIgnore @RequestParam Map<String, Object> paramMap);


	@PostMapping(PREFIX)
	@ApiOperation(value = "发送安防预警信息", notes = "发送安防预警信息")
	@DynamicParameters(name = "SecurityMessageMap",properties = {
			@DynamicParameter(name = "warnType",value = "安防预警类别"),
			@DynamicParameter(name = "warnMessage",value = "安防预警信息"),
	})
	DTO sendSecurityMessage(@RequestBody Map<String, Object> paramMap);

}
