package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysWebLogDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * @author 张思雨
 * @since 2019年9月17日13:46:15
 */
@Api(value = "用户中心：用户日志管理", tags = {"用户中心：用户日志管理"})
public interface SysLogResource {

	String PREFIX = "${user-center:}/v2/userLog";

	@GetMapping(PREFIX)
	@ApiOperation(value = "用户操作日志日志分页查询", notes = "用户操作日志日志分页查询")
	@ZjWorkFlow("UserLogQry")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "用户中文名称", paramType = "query"),
			@ApiImplicitParam(name = "method", value = "方法类型", paramType = "query"),
			@ApiImplicitParam(name = "result", value = "请求结果", paramType = "query"),
			@ApiImplicitParam(name = "requestStartDate", value = "时间范围--从什么时候开始", paramType = "query"),
			@ApiImplicitParam(name = "requestEndDate", value = "时间范围--到什么时候结束", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<SysWebLogDTO> qrySysWebLogByPage(@ApiIgnore @RequestParam HashMap<String, Object> paramMap);

}
