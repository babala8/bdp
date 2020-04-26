package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.service.TaskInnerService;
import com.zjft.microservice.treasurybrain.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-02
 */
@Slf4j
@RestController
public class TaskInnerResourceImpl implements TaskInnerResource {
	@Resource
	private TaskInnerService taskInnerService;

	@Resource
	private TaskService taskService;


	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskInnerService.insertSelectiveByMap(paramMap);
	}

	@Override
	public String getNextLogicId(String clrCenterNo, int taskType) {
		return taskInnerService.getNextLogicId(clrCenterNo,taskType);
	}

	@Override
	public String getClrCenterNo(String taskNo) {
		return taskInnerService.getClrCenterNo(taskNo);
	}

	@Override
	public String getShelfNo(String taskNo) {
		return taskInnerService.getShelfNo(taskNo);
	}

	@Override
	public TaskTableDTO getByPrimaryKey(String taskNo) {
		return taskInnerService.getByPrimaryKey(taskNo);
	}

	@Override
	public int updateByPrimaryKeyMap(Map<String, Object> paramMap) {
		return taskInnerService.updateByPrimaryKeyMap(paramMap);
	}

	@Override
	public List<String> selectTaskNoByLineNo(Map<String, Object> paramMap) {
		return taskInnerService.selectTaskNoByLineNo(paramMap);
	}

	@Override
	public List<String> qryTaskNoByLineNo(Map<String, Object> paramMap) {
		return taskInnerService.qryTaskNoByLineNo(paramMap);
	}

	@Override
	public Integer getStatus(String taskNo) {
		return taskInnerService.getStatus(taskNo);
	}

	@Override
	public int cashInStorage(List<String> taskNos, Integer status) {
		return taskInnerService.cashInStorage(taskNos,status);
	}

	@Override
	public List<String> getTodayLine(String status, String planFinishDate, String opNo) {
		return taskInnerService.getTodayLine(status,planFinishDate,opNo);
	}

	@Override
	public List<Map<String, Object>> qryLineNoList(Map<String, Object> paramMap) {
		return taskInnerService.qryLineNoList(paramMap);
	}

	@Override
	public List<StorageUseEntityTransferDTO> getLine(String shelfNo) {
		return taskInnerService.getLine(shelfNo);
	}

	@Override
	public List<String> getTaskListByShelfNo(String shelfNo) {
		return taskInnerService.getTaskListByShelfNo(shelfNo);
	}

	@Override
	public List<String> getTaskListByShelfNoAndTaskType(String shelfNo, Integer taskType) {
		return taskInnerService.getTaskListByShelfNoAndTaskType(shelfNo,taskType);
	}

	@Override
	public int qryTotalRowStorage(Map<String, Object> paramMap) {
		return taskInnerService.qryTotalRowStorage(paramMap);
	}

	@Override
	public List<StorageUseEntityDTO> qryStorageEntityByPage(Map<String, Object> paramMap) {
		return taskInnerService.qryStorageEntityByPage(paramMap);
	}

	@Override
	public StorageUseEntityDetailDTO getStorageInfo(String taskNo) {
		return taskInnerService.getStorageInfo(taskNo);
	}

	@Override
	public List<Map<String, Object>> qryShelfNoByLineNo(Map<String, Object> paramMap) {
		return taskInnerService.qryShelfNoByLineNo(paramMap);
	}

	@Override
	public List<String> qryShelfDetailByShelfNo(Map<String, Object> paramMap) {
		return taskInnerService.qryShelfDetailByShelfNo(paramMap);
	}

	@Override
	public Integer qryCurStatus(String taskNo) {
		return taskInnerService.qryCurStatus(taskNo);
	}

	@Override
	public int qryTotalRowTauro(Map<String, Object> paramMap) {
		return taskInnerService.qryTotalRowTauro(paramMap);
	}

	@Override
	public List<TaskTauroDTO> qryDispatchByPage(Map<String, Object> paramMap) {
		return taskInnerService.qryDispatchByPage(paramMap);
	}

	@Override
	public Integer qryTaskType(String taskNo) {
		return taskInnerService.qryTaskType(taskNo);
	}

	@Override
	public int insertTaskLineByMap(Map<String, Object> paramMap) {
		return taskInnerService.insertTaskLineByMap(paramMap);
	}

	@Override
	public String getDev(String taskNo) {
		return taskInnerService.getDev(taskNo);
	}

	@Override
	public List<TaskProductDTO> qryTaskDetailList(String taskNo) {
		return taskInnerService.qryTaskDetailList(taskNo);
	}

	@Override
	public List<TaskEntityDTO> qryEntityNoLists(String taskNo) {
		return taskInnerService.qryEntityNoLists(taskNo);
	}

}
