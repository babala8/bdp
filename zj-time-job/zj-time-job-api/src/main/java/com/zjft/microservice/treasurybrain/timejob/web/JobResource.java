package com.zjft.microservice.treasurybrain.timejob.web;

import com.zjft.microservice.quartz.dto.BaseDTO;
import com.zjft.microservice.quartz.dto.PageDTO;
import com.zjft.microservice.quartz.dto.TimeJobDTO;
import com.zjft.microservice.quartz.dto.TimeJobParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author cwang
 */
@Api(value = "定时任务模块：定时任务管理", tags = {"定时任务模块：定时任务管理"})
public interface JobResource {

	String PREFIX = "${time-job:}/v2/job";

	@GetMapping(PREFIX)
	@ApiOperation(value = "查询定时任务列表", notes = "查询定时任务列表")
	PageDTO qryTimeJob(@ModelAttribute TimeJobParamDTO timeJobParamDTO);

	@PutMapping(PREFIX)
	@ApiOperation(value = "修改定时任务", notes = "修改定时任务")
	BaseDTO modTimeJob(@RequestBody TimeJobDTO timeJobDTO);

	@PostMapping(PREFIX)
	@ApiOperation(value = "添加定时任务", notes = "添加定时任务")
	BaseDTO addTimeJob(@RequestBody TimeJobDTO timeJobDTO);

	@DeleteMapping(PREFIX + "/{jobID}")
	@ApiOperation(value = "删除定时任务", notes = "删除定时任务")
	@ApiImplicitParam(name = "jobID", value = "定时任务编号", required = true, paramType = "path")
	BaseDTO delTimeJob(@PathVariable("jobID") String jobID);
}
