package com.zjft.microservice.treasurybrain.timejob.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
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
 * @since 2019-08-05
 */
@Api(value = "定时任务模块：定时任务日志管理", tags = {"定时任务模块：定时任务日志管理"})
public interface JobLogResource {

	String PREFIX = "${time-job:}/v2/jobLog";

	@GetMapping(PREFIX)
	@ApiOperation(value = "定时任务日志分页查询", notes = "定时任务日志分页查询")
	@ZjWorkFlow("jobLogQry")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jobName", value = "定时任务名称", example = "ETLJob", paramType = "query"),
			@ApiImplicitParam(name = "jobType", value = "定时任务类型", example = "0", paramType = "query"),
			@ApiImplicitParam(name = "jobResult", value = "定时任务结果", example = "0", paramType = "query"),
			@ApiImplicitParam(name = "jobCreator", value = "定时任务创建者", example = "NULL", paramType = "query"),
			@ApiImplicitParam(name = "startTime", value = "定时任务开始时间", example = "2019-07-11 15:07:00", paramType = "query"),
			@ApiImplicitParam(name = "endTime", value = "定时任务结束时间", example = "2019-07-11 15:07:00", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO qryTimeJobLogByPage(@ApiIgnore @RequestParam HashMap<String, Object> paramMap);
}
