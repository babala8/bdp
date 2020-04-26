package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderCoordsDTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TaskProcessDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TaskProcessDetailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/8/9
 */
@Api(value = "物流中心：任务流转", tags = "物流中心：任务流转")
public interface TaskProcessResource {
	String PREFIX = "${tauro:}/v2/taskProcess";

	/**
	 * 分页查询流转任务信息
	 */
	@GetMapping(PREFIX)
	@ApiOperation(value = "查询流转任务信息", notes = "查询流转任务信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "taskNo", value = "任务单编号", paramType = "query"),
			@ApiImplicitParam(name = "opNo", value = "加钞人员编号",  paramType = "query"),
			@ApiImplicitParam(name = "taskType", value = "任务单类型",  paramType = "query"),
			@ApiImplicitParam(name = "lineNo", value = "线路编号",  paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<TaskProcessDTO> queryDispatchByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	@GetMapping(PREFIX + "/{taskNo}")
	@ApiOperation(value = "查询流转任务信息详情", notes = "查询流转任务信息详情")
	@ApiImplicitParam(name = "taskNo", value = "任务单编号", required = true, paramType = "path")
	TaskProcessDetailDTO queryDispatchDetail(@PathVariable("taskNo") String taskNo);

	/**
	 * 任务流转监控--坐标心跳包
	 *
	 * @param tagReaderCoordsDTO 手持机记录信息DTO
	 * @return 是否成功
	 */
	@PostMapping(PREFIX + "/coords")
	@ApiOperation(value = "增加手持机坐标信息", notes = "增加手持机坐标信息")
	DTO addTagReaderCoordsInfo(@RequestBody TagReaderCoordsDTO tagReaderCoordsDTO);

	/**
	 * 任务流转监控--查询手持机在流转工程中的位置记录
	 *
	 * @param taskNo 任务单编号
	 * @return ListDTO<TagReaderCoordsDTO>
	 */
	@GetMapping(PREFIX + "/coords/{taskNo}")
	@ApiOperation(value = "查询手持机在流转工程中的位置记录", notes = "查询手持机在流转工程中的位置记录")
	@ApiImplicitParam(name = "taskNo", value = "任务单编号", example = "JCRW201812130001", required = true, paramType = "path")
	ListDTO<TagReaderCoordsDTO> queryCoordsByTaskNo(@PathVariable("taskNo") String taskNo);
}
