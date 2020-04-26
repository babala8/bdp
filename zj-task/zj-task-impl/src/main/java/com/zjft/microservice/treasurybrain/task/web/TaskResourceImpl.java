package com.zjft.microservice.treasurybrain.task.web;

import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.dto.TaskDTO;
import com.zjft.microservice.treasurybrain.task.service.TaskDetailService;
import com.zjft.microservice.treasurybrain.task.service.TaskService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @author wupeng
 * @author 韩通
 * @since 2019/8/5 09:58
 */
@Slf4j
@RestController
public class TaskResourceImpl implements TaskResource {

	@Resource
	private TaskService taskService;

	@Resource
	private TaskDetailService taskDetailService;

	@Resource
	private SysUserResource sysUserResource;

	 /**
	 * 分页查询
	 */
	@Override
	public PageDTO<TaskInfoDTO> qryByPage(Map<String, Object> paramMap) {
		PageDTO<TaskInfoDTO> pageDTO = new PageDTO<TaskInfoDTO>();
		try {

			Map<String,Object> retMap  = taskService.qryTask(paramMap);

			List<TaskInfoDTO> retList = (List<TaskInfoDTO>) retMap.get("retList");
			pageDTO.setTotalRow(StringUtil.ch2Int(String.valueOf(retMap.get("totalRow"))));
			pageDTO.setTotalPage(StringUtil.ch2Int(String.valueOf(retMap.get("totalPage"))));
			pageDTO.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			pageDTO.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			pageDTO.setPageSize(StringUtil.ch2Int(String.valueOf(retMap.get("pageSize"))));
			pageDTO.setCurPage(StringUtil.ch2Int(String.valueOf(retMap.get("curPage"))));
			pageDTO.setRetList(retList);
		} catch (Exception e) {
			log.error("查询失败", e);
			pageDTO.setRetException("查询失败");
		}
		return pageDTO;
	}

	/**
	 * 根据设备号查询当天任务单查询详情
	 *
	 * @param
	 * @return
	 */
	@Override
	public ListDTO<TaskInfoWithDetailDTO> qryDetailByCustomerNo(Map<String, Object> paramMap) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());

		try {
			String customerNo = StringUtil.parseString(paramMap.get("customerNo"));
			String sysDate = CalendarUtil.getSysTimeYMD();
			String status = StringUtil.parseString(paramMap.get("status"));
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("customerNo", customerNo);
			params.put("sysDate", sysDate);
			params.put("status", status);
			String taskNo = taskService.qryTaskByCustomerNo(params);

			Map<String, Object> retMap = taskService.qryOwnTaskDetail(taskNo);
			List retList = (List) retMap.get("retList");
			dto.setRetList(retList);

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("根据设备号查询当天任务单查询详情失败", e);
			dto.setRetException("根据设备号查询当天任务单查询详情失败!");
		}
		return dto;
	}

	/**
	 * 根据设备号查询设备上一个任务使用的容器
	 * @param customerNo
	 * @return
	 */
	@Override
	public ListDTO<String> qryPreviousContainByCustomerNo(String customerNo) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			return taskDetailService.qryPreviousContainByCustomerNo(customerNo);
		} catch (Exception e) {
			log.error("根据设备号查询设备上一个任务使用的容器", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}


	/**
	 * 分配人员
	 * <p>
	 * 已开始执行的任务无法分配人员
	 */
	@Override
	public DTO assignOp(TaskInfoDTO taskInfoDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			dto = taskService.assignTo(taskInfoDTO);
		} catch (Exception e) {
			log.error("分配人员失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}

		return dto;
	}

	/**
	 * 取消任务单
	 * <p>
	 * 更新任务状态为已取消，开始执行的任务无法取消
	 */
	@Override
	public DTO cancel(String taskNo) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "操作失败");
		try {
			int status = StatusEnum.TaskStatusTauro.STATUS_CANCELLD_DEPRECATED.getStatus();
			Map<String, Object> retMap = taskService.cancel(taskNo, status);

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("取消加钞任务失败 ", e);
			dto.setRetException("取消加钞任务失败");
		}
		return dto;
	}

	/**
	 * 查询当前用户当天可执行的任务明细
	 */
	@Override
	public ListDTO<TaskInfoDTO> qryOwnTaskDetail(String taskNo) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			Map<String, Object> retMap = taskService.qryOwnTaskDetail(taskNo);
			List retList = (List) retMap.get("retList");
			dto.setRetList(retList);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询当前用户当天可执行的任务失败 ", e);
			dto.setRetException("查询当前用户当天可执行的任务失败");
		}
		return dto;
	}

	/**
	 * 根据线路号，任务单状态查询任务单明细
	 */
	@Override
	public ListDTO<TaskInfoDTO> qryTaskByLineNo(Map<String, Object> paramMap) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			Map<String, Object> retMap = taskService.qryTaskByLineNo(paramMap);
			List retList = (List) retMap.get("retList");
			dto.setRetList(retList);

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询当前用户当天可执行的任务失败 ", e);
			dto.setRetException("查询当前用户当天可执行的任务失败");
		}
		return dto;


	}

	/**
	 * 查询加钞任务导出信息
	 */
	@Override
	public ObjectDTO qryExportTask(Map<String, Object> paramMap) {
		log.info("------------[qryExportTask]-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			String taskhNos = JSONUtil.createJsonString(paramMap.get("noArr"));
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("taskNos", taskhNos);

			Map<String, Object> retMap = taskService.qryExportTask(JSONUtil.createJsonString(params));
			dto.setElement(retMap);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("查询加钞任务导出信息失败 ", e);
			dto.setRetException("查询加钞任务导出信息失败");
		}
		return dto;
	}

	/**
	 * 根据容器号查询任务单明细
	 *
	 * @return 结果
	 */
	@Override
	public ObjectDTO qryTaskDetailByContainerNo(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {

			Map<String, Object> retMap = taskService.qryTaskDetailByContainerNo(paramMap);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("查询失败" + e.getMessage(), e);
			dto.setRetException("查询失败!");
		}
		return dto;
	}


	/**
	 * 根据线路编号查询笼车明细（出库）
	 */
	@Override
	public ListDTO qryShelfDetailByLineNo(Map<String, Object> paramMap) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {
			Map<String, Object> shelfDetailList = taskService.qryShelfDetailByLineNo(paramMap);
			List retList = (List) shelfDetailList.get("retList");
			dto.setRetList(retList);
			dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto.setRetMsg(RetCodeEnum.SUCCEED.getTip());
		} catch (Exception e) {
			log.error("查询线路失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}

	/**
	 * 任务单导出
	 *
	 * @param exportInfo
	 * @return 结果
	 */
	@Override
	public DTO exportAddnotesTaskExcel(HttpServletRequest request, HttpServletResponse response, ExportTaskExcelDTO exportInfo) {

		try {
			return taskService.exportAddnotesTaskExcel(request, response, exportInfo);
		} catch (Exception e) {
			log.error("发生导出任务单异常", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}

	}

	/**
	 * 加钞任务单过期
	 *
	 * @return
	 */
	@Override
	public DTO taskOutTime(Map<String, Object> paramMap) {

		return taskService.taskOutTime(paramMap);

	}

	/**
	 * 网点寄库&解现任务单拆分
	 */
	@Override
	public DTO taskSplit(TaskSplitDTO taskSplitDTO) {
		log.info("------------TaskResourceImpl[taskSplit]-------------");
		try {
			//参数校验：任务单编号，网点编号，容器分组列表不为空
			if (StringUtil.isNullorEmpty(taskSplitDTO.getClrCenterNo()) || taskSplitDTO.getTaskSplitContainerLists().isEmpty()
					|| taskSplitDTO.getTaskSplitContainerLists() == null || StringUtil.isNullorEmpty(taskSplitDTO.getCustomerNo())) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return taskService.taskSplit(taskSplitDTO);
		}catch (Exception e) {
			log.error("网点任务单拆分失败：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 网点寄库&解现任务单合并
	 */
	@Override
	public DTO taskMerge(List<String> taskNoList) {
		log.info("------------TaskResourceImpl[taskMerge]-------------");
		try {
			//参数校验：任务单编号列表不为空
			if (taskNoList.size()<1) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return taskService.taskMerge(taskNoList);
		}catch (Exception e) {
			log.error("网点任务单合并失败：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 钞处中心配款清分信息查询
	 * @return
	 */
	@Override
	public ObjectDTO qryBankNote(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {
			Map<String, Object> retMap = taskService.qryBankNote(paramMap);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retMap);
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}


	/**
	 * 任务单基础信息修改
	 */
	@Override
	public DTO updateTaskInfo(TaskInfoDTO taskInfoDTO){
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			String taskNo = taskInfoDTO.getTaskNo();
			if(StringUtil.isNullorEmpty(taskNo)){
				dto.setResult(RetCodeEnum.PARAM_LACK);
				return dto;
			}
			dto = taskService.updateTaskInfo(taskInfoDTO);
		} catch (Exception e) {
			log.error("任务单基础信息修改", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	/**
	 * 删除任务单
	 * @param taskNo
	 * @return 返回信息
	 */
	@Override
	public DTO delTask(String taskNo) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"删除失败");
		try {
			dto = taskService.delTask(taskNo);
		}catch (Exception e ){
			log.error("删除任务单失败:", e);
			dto.setRetException("删除任务单失败!");
		}
		return dto;
	}

	/**
	 * 钞处领现确认出库批量
	 */
	@Override
	public DTO affirmOutBatchNotesReceipt(TaskInfoDTO taskInfoDTO){
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "失败");
		try {
			return taskService.affirmOutBatchNotesReceipt(taskInfoDTO);
		}catch (Exception e) {
			log.error("批量出库失败:", e);
			dto.setRetException("批量出库失败");
			return dto;
		}
	}

	/**
	 * 网点调拨审核任务单（网点管理员、金库人员）
	 *
	 * @param transferTaskInfoDTO 审核信息
	 * @return 返回信息
	 */
	@Override
	public DTO auditTransfer(TransferTaskInfoDTO transferTaskInfoDTO) {
		log.info("------------[modTransfer]-------------");
		DTO dto1 = new DTO(RetCodeEnum.FAIL.getCode(), "审核失败");
		try {
			dto1 = taskDetailService.modDevClrTime(transferTaskInfoDTO);
		}catch (Exception e) {
			log.error("网点调拨任务单审核失败:", e);
			dto1.setRetException("网点调拨任务单审核失败");
			return dto1;
		}
		return dto1;
	}

	/**
	 * 根据任务单编号查询历史节点
	 * @param taskNo
	 * @return
	 */
	@Override
	public ListDTO<TaskNodeDTO> qryTaskNode(String taskNo) {
		ListDTO<TaskNodeDTO> listDTO = new ListDTO<>(RetCodeEnum.EXCEPTION);
		if (StringUtil.isNullorEmpty(taskNo)) {
			listDTO.setResult(RetCodeEnum.PARAM_LACK);
			return listDTO;
		}
		try {
			return taskService.qryTaskNode(taskNo);
		} catch (Exception e) {
			e.printStackTrace();
			listDTO.setRetCode(RetCodeEnum.EXCEPTION.getCode());
			listDTO.setRetMsg("查询操作记录失败！");
			return listDTO;
		}
	}

	/**
	 * 清点信息查询：根据任务单编号查询清点信息
	 */
	@Override
	public TaskDTO qryInventoryInfo(String taskNo){
		try {
			return taskDetailService.qryLoadNoteDetail(taskNo);
		} catch (Exception e) {
			log.error("[查询清点信息详情异常]", e);
			return new TaskDTO(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 配钞信息查询：分页查询清点信息
	 */
	@Override
	public PageDTO<LoadNoteDTO> qryLoadNoteByPage(Map<String, Object> paramMap) {
		try {
			return taskDetailService.qryLoadNoteByPage(paramMap);
		} catch (Exception e) {
			log.error("[分页查询情定信息异常]", e);
			return new PageDTO<>(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 配钞信息查询：分页查询配钞信息
	 */
	@Override
	public PageDTO<LoadNoteDTO> qryLoadInfoByPage(Map<String, Object> paramMap) {
		try {
			return taskDetailService.qryLoadInfoByPage(paramMap);
		} catch (Exception e) {
			log.error("[分页查询配钞信息异常]", e);
			return new PageDTO<>(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 配钞信息查询：根据任务单编号查询配钞信息
	 */
	@Override
	public LoadNoteDTO qryLoadInfo(String taskNo){
		try {
			return taskDetailService.qryLoadInfo(taskNo);
		} catch (Exception e) {
			log.error("[分页查询配钞信息异常]", e);
			return new LoadNoteDTO(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 配钞清分信息查询：分页查询配钞清分信息
	 */
	@Override
	public PageDTO<TaskCountInfoDTO> qryCountTaskList(Map<String, Object> paramMap) {
		try {
			return taskDetailService.qryCountTaskListByPage(paramMap);
		} catch (Exception e) {
			log.error("[分页查询配钞清分信息异常]", e);
			return new PageDTO<>(RetCodeEnum.FAIL);
		}
	}

}
