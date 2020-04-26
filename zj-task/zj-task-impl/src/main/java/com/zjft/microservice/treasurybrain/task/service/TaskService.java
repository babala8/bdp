package com.zjft.microservice.treasurybrain.task.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.task.domain.TaskDetail;
import com.zjft.microservice.treasurybrain.task.dto.ExportTaskExcelDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskNodeDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskSplitDTO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface TaskService {

	/**
	 * 查询加钞任务
	 * @param paramMap 查询条件
	 * @return Map
	 */
	Map<String,Object> qryTask(Map<String, Object> paramMap);

	/**
	 * 加钞任务分配人员
	 * @param dto 分配人员信息
	 * @return 结果
	 */
	DTO assignTo(TaskInfoDTO dto);

	/**
	 * 取消加钞计划
	 *
	 * @param  taskNo, status
	 * @return 状态码
	 */
	Map<String, Object> cancel(String taskNo,int status);

	/**
	 * 查询加钞任务导出信息
	 * @param createJsonString 查询条件
	 * @return 结果
	 */
	Map<String,Object> qryExportTask(String createJsonString);

	/**
	 * 查询自己当天可执行的任务明细
	 * @return 结果
	 */
	Map<String,Object> qryOwnTaskDetail(String taskNo);

	/**
	 * 根据线路查询任务单明细
	 * @return 结果
	 */
	Map<String,Object> qryTaskByLineNo(Map<String, Object> paramMap);


	/**
	 * 根据设备号和状态查询当日任务单号
	 * @return 结果
	 */
	String  qryTaskByCustomerNo(Map<String, Object> paramMap);


	/**
	 * 根据容器号查询任务单明细
	 * @return 结果
	 */
	Map<String,Object>  qryTaskDetailByContainerNo(Map<String, Object> paramMap);


	/**
	 * 查询可执行线路
	 * @return 结果
	 */
	Map<String,Object> qryLineNoList(Map<String, Object> paramMap);

	/**
	 * 根据线路编号查询笼车明细（出库）
	 */
	Map<String,Object> qryShelfDetailByLineNo(Map<String, Object> paramMap);

	/**
	 * 任务单导出
	 * @return 结果
	 */
	DTO  exportAddnotesTaskExcel(HttpServletRequest request, HttpServletResponse response, ExportTaskExcelDTO exportInfo) throws Exception;

	/**
	 *
	 * 加钞任务单过期
	 *
	 * @return
	 */
	DTO taskOutTime(Map<String,Object> paramMap);

	/**
	 * 任务单拆分
	 *
	 * @param taskSplitDTO 原任务单及拆分箱子列表信息
	 * @return 返回信息
	 */
	DTO taskSplit(TaskSplitDTO taskSplitDTO);

	/**
	 * 任务单合并
	 *
	 * @param taskNoList 需要合并的任务单编号列表
	 * @return 返回信息
	 */
	DTO taskMerge(List<String> taskNoList);


	/**
	 * 钞处中心待执行任务信息查询
	 * @return
	 */
	Map<String,Object> qryBankNote(Map<String, Object> paramMap);

	/**
	 * 任务单基础信息修改
	 *
	 * @param taskInfoDTO 需要修改的任务单信息
	 * @return 返回信息
	 */
	DTO updateTaskInfo(TaskInfoDTO taskInfoDTO);

	/**
	 * 删除任务单
	 */
	DTO delTask(String taskNo);

	/**
	 * 批量出库
	 */
	DTO affirmOutBatchNotesReceipt(TaskInfoDTO taskInfoDTO);

	/**
	 * 根据任务单编号查询历史节点
	 * @param taskNo 任务编号
	 * @return
	 */
	ListDTO<TaskNodeDTO> qryTaskNode(String taskNo);

	/**
	 * 根据任务单编号查询金额明细
	 * @param taskNo 任务编号
	 * @return
	 */
	List<TaskDetail> qryTaskDetailList(String taskNo);

	/**
	 * 根据任务单编号查询箱子及箱子金额明细
	 * @param taskNo 任务编号
	 * @return
	 */
	List<TaskEntityPO> qryEntityNoLists(String taskNo);
}
