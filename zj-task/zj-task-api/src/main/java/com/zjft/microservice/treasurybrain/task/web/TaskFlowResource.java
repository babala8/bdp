package com.zjft.microservice.treasurybrain.task.web;

import com.zjft.microservice.orchestration.core.annotations.ZjWorkFlow;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * @author liuyuan
 * @since 2019/8/7 08:53
 */
@Api(value = "任务中心：任务单管理（工作流）", tags = "任务中心：任务单管理（工作流）")
public interface TaskFlowResource {

	String PREFIX = "${task:}/v2/taskFlow";

	/**
	 * 删除任务单
	 *
	 * @param taskNo
	 * @return 返回信息
	 */
	@DeleteMapping(PREFIX + "/{taskNo}")
	@ApiOperation(value = "删除任务单（工作流）", notes = "删除任务单（工作流）")
	@ApiImplicitParam(name = "taskNo", value = "任务单编号", required = true, paramType = "path")
	@ZjWorkFlow("delTask")
	DTO delTask(@PathVariable("taskNo") String taskNo);

	/**
	 * 分页查询
	 *
	 * @param paramMap
	 * @return
	 */
	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询任务单列表（工作流）", notes = "分页查询任务单列表（工作流）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productType", value = "产品编号（1：自定义产品 0：固定产品），默认为0", paramType = "query"),
			@ApiImplicitParam(name = "taskNo", value = "任务编号", paramType = "query"),
			@ApiImplicitParam(name = "clrCenterNo", value = "金库编号", paramType = "query"),
			@ApiImplicitParam(name = "taskType", value = "任务单类型", paramType = "query"),
			@ApiImplicitParam(name = "lineNo", value = "押运线路", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query"),
			@ApiImplicitParam(name = "startDate", value = "开始时间", paramType = "query"),
			@ApiImplicitParam(name = "endDate", value = "结束时间", paramType = "query"),
			@ApiImplicitParam(name = "operateType", value = "操作类型", paramType = "query"),
			@ApiImplicitParam(name = "addnotesPlanNo", value = "加钞编号", paramType = "query"),
			@ApiImplicitParam(name = "checkAll", value = "是否查询所有任务单（1：所有 0：当前登录人），默认为1", defaultValue = "1", paramType = "query")
	})
	@ZjWorkFlow("qryByPage")
	PageDTO qryByPage(@ApiIgnore @RequestParam HashMap<String, Object> paramMap);

	/**
	 * 任务单基础信息修改
	 */
	@PutMapping(PREFIX)
	@ApiOperation(value = "任务单基础信息修改（工作流）", notes = "任务单基础信息修改（工作流）")
	@ZjWorkFlow("updateTaskInfo")
	DTO updateTaskInfo(@RequestBody TaskInfoDTO taskInfoDTO);

}
