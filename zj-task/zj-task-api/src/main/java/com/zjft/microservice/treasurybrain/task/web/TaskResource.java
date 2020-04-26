package com.zjft.microservice.treasurybrain.task.web;

import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.dto.TaskDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/8/7 08:53
 */
@Api(value = "任务中心：任务单管理", tags = "任务中心：任务单管理")
public interface TaskResource {

	String PREFIX = "${task:}/v2/task";

	/**
	 * 分页查询  已改
	 *
	 * @param paramMap
	 * @return
	 */
	@GetMapping(PREFIX)
	@ApiOperation(value = "分页查询任务单列表", notes = "分页查询任务单列表")
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
			@ApiImplicitParam(name = "checkAll", value = "是否查询所有任务单（1：所有 0：当前登录人），默认为1", defaultValue = "1", paramType = "query"),
			@ApiImplicitParam(name = "direction", value = "调入调出方向，默认为1", defaultValue = "1", paramType = "query")
	})
	PageDTO<TaskInfoDTO> qryByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/**
	 * 任务单基础信息修改  已改
	 */
	@PutMapping(PREFIX)
	@ApiOperation(value = "任务单基础信息修改", notes = "任务单基础信息修改")
	DTO updateTaskInfo(@RequestBody TaskInfoDTO taskInfoDTO);

	/**
	 * 批量修改任务单状态 已改
	 */
	@PutMapping(PREFIX+"/taskStatusBatch")
	@ApiOperation(value = "批量修改任务单状态", notes = "批量修改任务单状态")
	DTO affirmOutBatchNotesReceipt(@RequestBody TaskInfoDTO taskInfoDTO);

	/**
	 * 删除任务单  已改
	 *
	 * @param taskNo
	 * @return 返回信息
	 */
	@DeleteMapping(PREFIX + "/{taskNo}")
	@ApiOperation(value = "删除任务单", notes = "删除任务单")
	@ApiImplicitParam(name = "taskNo", value = "任务单编号", required = true, paramType = "path")
	DTO delTask(@PathVariable("taskNo") String taskNo);

	/**
	 * 分配人员  已改
	 * <p>
	 * 已开始执行的任务无法分配人员
	 *
	 * @param taskInfoDTO 任务编号 人员1 人员2
	 * @return
	 */
	@PutMapping(PREFIX + "/operator")
	@ApiOperation(value = "分配人员", notes = "分配人员")
	DTO assignOp(@RequestBody TaskInfoDTO taskInfoDTO);

	/**
	 * 取消任务单 任务中心:取消任务
	 * <p>
	 * 更新任务状态为已取消，开始执行的任务无法取消
	 *
	 * @param taskNo 任务单编号
	 * @return
	 */
	@PutMapping(PREFIX + "/cancel/{taskNo}")
	@ApiOperation(value = "任务中心:取消任务", notes = "任务中心:取消任务")
	DTO cancel(@PathVariable("taskNo") String taskNo);

	/**
	 * 加钞任务单过期
	 *
	 * @return
	 */
	DTO taskOutTime(Map<String,Object> map);

	/**
	 * 任务单导出
	 *
	 * @param exportInfo
	 * @return 结果
	 */
	@PostMapping(PREFIX + "/exportTaskExcel")
	@ApiOperation(value = "任务单导出", notes = "任务单导出")
	DTO exportAddnotesTaskExcel(@ApiIgnore HttpServletRequest request,
								@ApiIgnore HttpServletResponse response,
								@RequestBody ExportTaskExcelDTO exportInfo);

	/**
	 * 查询加钞任务导出信息
	 */
	@GetMapping(PREFIX + "/exportTask")
	@ApiOperation(value = "查询加钞任务导出信息", notes = "查询加钞任务导出信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "noArr", value = "加钞计划编号数组", required = true, paramType = "query")
	})
	ObjectDTO qryExportTask(@ApiIgnore @RequestParam Map<String, Object> paramMap);



	/** 已改
	 * 查询任务单明细
	 *
	 * @return
	 */
	@GetMapping(PREFIX + "/{taskNo}")
	@ApiOperation(value = "查询任务单明细", notes = "查询任务单明细")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "taskNo", value = "任务编号", required = true, paramType = "path")
	})
	ListDTO<TaskInfoDTO> qryOwnTaskDetail(@PathVariable("taskNo") String taskNo);

	/** 已改
	 *
	 * @param paramMap
	 * @return
	 */
	@GetMapping(PREFIX + "/qryByLineNo")
	@ApiOperation(value = "根据线路编号查询任务单明细", notes = "根据线路编号查询任务单明细")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lineNo", value = "线路编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "operateType", value = "操作类型", paramType = "query"),
			@ApiImplicitParam(name = "taskType", value = "任务单类型", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "任务单状态", paramType = "query"),
			@ApiImplicitParam(name = "planFinishDate", value = "计划完成日期", paramType = "query")
	})
	ListDTO<TaskInfoDTO> qryTaskByLineNo(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/** 已改
	 * 根据容器号查询设备明细
	 */
	@GetMapping(PREFIX + "/qryBycontainerNo")
	@ApiOperation(value = "根据容器号查询任务单明细", notes = "根据容器号查询任务单明细")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "entityNo", value = "容器编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "productNo", value = "容器类型", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "任务单状态",  paramType = "query"),
			@ApiImplicitParam(name = "operateType", value = "操作类型",  paramType = "query")
	})
	ObjectDTO qryTaskDetailByContainerNo(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/** 已改
	 * 根据设备号查询当天任务单查询详情
	 *
	 * @param
	 * @return
	 */
	@GetMapping(PREFIX + "/qryByCustomerNo")
	@ApiOperation(value = "根据设备号查询当天任务单查询详情", notes = "根据设备号查询当天任务单查询详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "customerNo", value = "设备号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "status", value = "状态", required = true, paramType = "query"),
	})
	ListDTO<TaskInfoWithDetailDTO> qryDetailByCustomerNo(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/**
	 * 网点寄库&解现任务单拆分
	 */
	@PostMapping(PREFIX+ "/split")
	@ApiOperation(value = "网点寄库&解现任务单拆分", notes = "网点寄库&解现任务单拆分")
	DTO taskSplit(@RequestBody TaskSplitDTO taskSplitDTO);

	/**
	 * 网点寄库&解现任务单合并
	 */
	@PostMapping(PREFIX+ "/merge")
	@ApiOperation(value = "网点寄库&解现任务单合并", notes = "网点寄库&解现任务单合并")
	DTO taskMerge(@RequestBody List<String> taskNoList);

	/** 已改
	 * 根据设备号查询设备上一个任务使用的容器
	 */
	@GetMapping(PREFIX + "/qryPreviousContain/{customerNo}")
	@ApiOperation(value = "根据设备号查询设备上一个任务使用的容器", notes = "根据设备号查询设备上一个任务使用的容器")
	@ApiImplicitParam(name = "customerNo", value = "设备编号", required = true, paramType = "path")
	ListDTO<String> qryPreviousContainByCustomerNo(@PathVariable("customerNo") String customerNo);

	/**
	 * 根据线路编号查询笼车明细（出库）
	 */
	@GetMapping(PREFIX + "/qryShelfDetailByLineNo")
	@ApiOperation(value = "根据线路编号查询笼车明细（出库）", notes = "根据线路编号查询笼车明细（出库）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lineNoList", value = "线路编号数组", required = true, paramType = "query"),
			@ApiImplicitParam(name = "taskType", value = "任务单类型", paramType = "query"),
			@ApiImplicitParam(name = "productNo", value = "容器类型", paramType = "query"),
	})
	ListDTO qryShelfDetailByLineNo(@ApiIgnore @RequestParam Map<String, Object> paramMap);



	/**已改
	 * 钞处中心待执行任务信息查询
	 * @return
	 */
	@GetMapping(PREFIX+"/bankNote")
	@ApiOperation(value = "钞处中心待执行任务信息查询",notes = "钞处中心待执行任务信息查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "taskType", value = "任务单类型" , paramType = "query"),
			@ApiImplicitParam(name = "operateType", value = "操作类型" , paramType = "query"),
			@ApiImplicitParam(name = "startDate", value = "起始日期" , paramType = "query"),
			@ApiImplicitParam(name = "endDate", value = "结束日期" , paramType = "query")
	})
	ObjectDTO  qryBankNote(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	/** 已改
	 * 网点调拨审核任务单（网点管理员、金库人员）
	 *
	 * @param transferTaskInfoDTO 审核信息
	 * @return 返回信息
	 */
	@PutMapping(PREFIX+"/audit")
	@ApiOperation(value = "网点调拨审核",notes = "网点调拨审核")
	DTO auditTransfer(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);

	//已改
	@GetMapping(PREFIX + "/operateRecord/{taskNo}")
	@ApiOperation(value = "根据任务编号查询记录信息", notes = "根据任务编号查询记录信息")
	@ApiImplicitParam(name = "taskNo", value = "任务编号", required = true, paramType = "path")
	ListDTO<TaskNodeDTO> qryTaskNode(@PathVariable("taskNo") String taskNo);

	//已改
	@GetMapping(PREFIX+"/inventory/{taskNo}")
	@ApiOperation(value = "清点信息查询", notes = "根据任务单编号查询清点信息")
	TaskDTO qryInventoryInfo(@PathVariable(value = "taskNo") String taskNo);

	//已改
	@GetMapping(PREFIX + "/loadCheckNote")
	@ApiOperation(value = "分页查询清点信息", notes = "分页查询清点信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "planStartDate", value = "计划开始日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "planEndDate", value = "计划结束日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "taskNo", value = "任务单编号", paramType = "query"),
			@ApiImplicitParam(name = "lineNo", value = "加钞线路", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "任务单状态", paramType = "query"),
			@ApiImplicitParam(name = "taskType", value = "任务单类型", paramType = "query")
	})
	PageDTO<LoadNoteDTO> qryLoadNoteByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	//已改
	@GetMapping(PREFIX + "/loadNote")
	@ApiOperation(value = "分页查询配钞信息", notes = "分页查询配钞信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "planStartDate", value = "计划开始日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "planEndDate", value = "计划结束日期[yyyy-MM-dd]", paramType = "query"),
			@ApiImplicitParam(name = "taskNo", value = "任务单编号", paramType = "query"),
			@ApiImplicitParam(name = "lineNo", value = "加钞线路", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query"),
			@ApiImplicitParam(name = "status", value = "任务单状态", paramType = "query"),
			@ApiImplicitParam(name = "taskType", value = "任务单类型", paramType = "query")
	})
	PageDTO<LoadNoteDTO> qryLoadInfoByPage(@ApiIgnore @RequestParam Map<String, Object> paramMap);

	//已改
	@GetMapping(PREFIX+"/loadNote/{taskNo}")
	@ApiOperation(value = "配钞信息查询", notes = "根据任务单编号查询配钞信息")
	LoadNoteDTO qryLoadInfo(@PathVariable(value = "taskNo") String taskNo);

	//已改
	@GetMapping(PREFIX + "/qryCountTaskList")
	@ApiOperation(value = "分页查询配钞清分信息", notes = "分页查询配钞清分信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo", value = "清机中心编号", paramType = "query"),
			@ApiImplicitParam(name = "taskNo", value = "任务单编号", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query"),
			@ApiImplicitParam(name = "countStatus", value = "清分状态", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "查询类别", paramType = "query"),
			@ApiImplicitParam(name = "taskType", value = "任务单类型", paramType = "query")
	})
	PageDTO<TaskCountInfoDTO> qryCountTaskList(@ApiIgnore @RequestParam Map<String, Object> paramMap);
}
