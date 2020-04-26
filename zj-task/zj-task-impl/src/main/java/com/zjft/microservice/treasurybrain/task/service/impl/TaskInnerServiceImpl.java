package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.task.domain.TaskDetail;
import com.zjft.microservice.treasurybrain.task.domain.TaskDetailPropertyDO;
import com.zjft.microservice.treasurybrain.task.domain.TaskInfo;
import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.mapstruct.TaskDetailConverter;
import com.zjft.microservice.treasurybrain.task.mapstruct.TaskEntityConverter;
import com.zjft.microservice.treasurybrain.task.mapstruct.TaskEntityDetailConverter;
import com.zjft.microservice.treasurybrain.task.mapstruct.TaskTableConverter;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityDetailPO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;
import com.zjft.microservice.treasurybrain.task.repository.*;
import com.zjft.microservice.treasurybrain.task.service.TaskInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-02
 */
@Slf4j
@Service
public class TaskInnerServiceImpl implements TaskInnerService {
	@Resource
	private TaskInfoMapper taskInfoMapper;

	@Resource
	private TaskDetailMapper taskDetailMapper;

	@Resource
	private TaskDetailPropertyMapper taskDetailPropertyMapper;

	@Resource
	private TaskEntityMapper taskEntityMapper;

	@Resource
	private TaskEntityDetailMapper taskEntityDetailMapper;

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskInfoMapper.insertSelectiveByMap(paramMap);
	}

	@Override
	public String getNextLogicId(String clrCenterNo, int taskType) {
		String currentDate = CalendarUtil.getSysTimeYMD();
		String currentDate1 = CalendarUtil.getDate().substring(2);
		String taskType1 = String.format("%02d", taskType);
		String logicId = clrCenterNo + taskType1 + currentDate1.replaceAll("-", "");
		String maxTaskNo = taskInfoMapper.selectByAddDate(clrCenterNo, currentDate, taskType);
		if (maxTaskNo != null) {
			String lastId = maxTaskNo;
			String seq = String.format("%04d", Integer.parseInt(lastId.substring(lastId.length() - 4)) + 1);
			logicId += seq;
		} else {
			logicId += "0001";
		}
		return logicId;
	}

	@Override
	public String getClrCenterNo(String taskNo) {
		return taskInfoMapper.getClrCenterNo(taskNo);
	}

	@Override
	public String getShelfNo(String taskNo) {
		return taskInfoMapper.getShelfNo(taskNo);
	}

	@Override
	public TaskTableDTO getByPrimaryKey(String taskNo) {
		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
		TaskTableDTO taskTableDTO = TaskTableConverter.INSTANCE.do2dto(taskInfo);
		return taskTableDTO;
	}

	@Override
	public int updateByPrimaryKeyMap(Map<String, Object> paramMap) {
		return taskInfoMapper.updateByPrimaryKeyMap(paramMap);
	}

	@Override
	public List<String> selectTaskNoByLineNo(Map<String, Object> paramMap) {
		return taskInfoMapper.selectTaskNoByLineNo(paramMap);
	}

	@Override
	public List<String> qryTaskNoByLineNo(Map<String, Object> paramMap) {
		return taskInfoMapper.qryTaskNoByLineNo(paramMap);
	}

	@Override
	public Integer getStatus(String taskNo) {
		return taskInfoMapper.getStatusByNo(taskNo);
	}

	@Override
	public int cashInStorage(List<String> taskNos, Integer status) {
		return taskInfoMapper.cashInStorage(taskNos, status);
	}

	@Override
	public List<String> getTodayLine(String status, String planFinishDate, String opNo) {
		return taskInfoMapper.getTodayLine(status, planFinishDate, opNo);
	}

	@Override
	public List<Map<String, Object>> qryLineNoList(Map<String, Object> paramMap) {
		return taskInfoMapper.qryLineNoList(paramMap);
	}

	@Override
	public List<StorageUseEntityTransferDTO> getLine(String shelfNo) {
		return taskInfoMapper.getLine(shelfNo);
	}

	@Override
	public List<String> getTaskListByShelfNo(String shelfNo) {
		return taskInfoMapper.getTaskListByShelfNo(shelfNo);
	}

	@Override
	public List<String> getTaskListByShelfNoAndTaskType(String shelfNo, Integer taskType) {
		return taskInfoMapper.getTaskListByShelfNoAndTaskType(shelfNo,taskType);
	}

	@Override
	public int qryTotalRowStorage(Map<String, Object> paramMap) {
		return taskInfoMapper.qryTotalRowStorage(paramMap);
	}

	@Override
	public List<StorageUseEntityDTO> qryStorageEntityByPage(Map<String, Object> paramMap) {
		return taskInfoMapper.qryStorageEntityByPage(paramMap);
	}

	@Override
	public StorageUseEntityDetailDTO getStorageInfo(String taskNo) {
		return taskInfoMapper.getStorageInfo(taskNo);
	}

	@Override
	public List<Map<String, Object>> qryShelfNoByLineNo(Map<String, Object> paramMap) {
		return taskInfoMapper.qryShelfNoByLineNo(paramMap);
	}

	@Override
	public List<String> qryShelfDetailByShelfNo(Map<String, Object> paramMap) {
		return taskInfoMapper.qryShelfDetailByShelfNo(paramMap);
	}

	@Override
	public Integer qryCurStatus(String taskNo) {
		return taskInfoMapper.qryCurStatus(taskNo);
	}

	@Override
	public int qryTotalRowTauro(Map<String, Object> paramMap) {
		return taskInfoMapper.qryTotalRowTauro(paramMap);
	}

	@Override
	public List<TaskTauroDTO> qryDispatchByPage(Map<String, Object> paramMap) {
		return taskInfoMapper.qryDispatchByPage(paramMap);
	}

	@Override
	public Integer qryTaskType(String taskNo) {
		return taskInfoMapper.selectTaskType(taskNo);
	}

	@Override
	public int insertTaskLineByMap(Map<String, Object> paramMap) {
		return taskInfoMapper.insertTaskLineByMap(paramMap);
	}

	@Override
	public String getDev(String taskNo) {
		return taskInfoMapper.getDev(taskNo);
	}

	@Override
	public List<TaskProductDTO> qryTaskDetailList(String taskNo) {
		List<TaskDetail> taskDetailList = taskDetailMapper.getDetailList(taskNo);
		List<TaskProductDTO> taskProductDTOList = TaskDetailConverter.INSTANCE.domain2dto1(taskDetailList);
//		List<TaskDetail> taskDetailList1 = new ArrayList<>();
		List<TaskProductDTO> taskProductDTOList1 = new ArrayList<>();
		for (TaskProductDTO taskProductDTO : taskProductDTOList) {
			String detailId = taskProductDTO.getId();
			List<TaskDetailPropertyDO> taskDetailPropertyDOList = taskDetailPropertyMapper.selectByDetailId(detailId);
			List<PropertyDTO> taskDetailPropertyDOList1 = new ArrayList<PropertyDTO>();

			for (TaskDetailPropertyDO taskDetailPropertyDO : taskDetailPropertyDOList) {
//				TaskDetailPropertyDO taskDetailPropertyDO1 = new TaskDetailPropertyDO();
				PropertyDTO propertyDTO = new PropertyDTO();
//				taskDetailPropertyDO1.setId(taskDetailPropertyDO.getId());
//				taskDetailPropertyDO1.setDetailId(taskDetailPropertyDO.getDetailId());
				propertyDTO.setKey(taskDetailPropertyDO.getKey());
				propertyDTO.setValue(taskDetailPropertyDO.getValue());
//				taskDetailPropertyDO1.setTaskNo(taskDetailPropertyDO.getTaskNo());
				propertyDTO.setName(taskDetailPropertyDO.getName());
				taskDetailPropertyDOList1.add(propertyDTO);
			}
			taskProductDTO.setDetailList(taskDetailPropertyDOList1);
			taskProductDTOList1.add(taskProductDTO);
		}
		return taskProductDTOList1;
	}

	@Override
	public List<TaskEntityDTO> qryEntityNoLists(String taskNo) {
		List<TaskEntityDTO> conList = new ArrayList<TaskEntityDTO>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("taskNo", taskNo);
		//判断任务单类型，如果是atm加钞任务单，则不查询direction为1（回收的箱子）的明细
		TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(taskNo);
		if (taskInfo.getTaskType() == 1) {
			paraMap.put("direction", 1);
		}
		List<TaskEntityPO> containerNoList = taskEntityMapper.selectContainerNoList(paraMap);
		List<TaskEntityDTO> taskEntityDTOList = TaskEntityConverter.INSTANCE.domain2dto(containerNoList);

		for (TaskEntityDTO containerNoList1 : taskEntityDTOList) {
			//查询容器类型
//			String containerNo = containerNoList1.getEntityNo();
			TaskEntityDTO containerNoList2 = new TaskEntityDTO();
			containerNoList2.setId(containerNoList1.getId());
			containerNoList2.setTaskNo(containerNoList1.getTaskNo());
			containerNoList2.setEntityNo(containerNoList1.getEntityNo());
			containerNoList2.setProductNo(containerNoList1.getProductNo());
			containerNoList2.setAmount(containerNoList1.getAmount());
			containerNoList2.setParentEntity(containerNoList1.getParentEntity());
			containerNoList2.setDirection(containerNoList1.getDirection());
			containerNoList2.setNote(containerNoList1.getNote());
			containerNoList2.setDepositType(containerNoList1.getDepositType());

			String id = containerNoList1.getId();
			List<TaskEntityDetailPO> currencyTypeLists = taskEntityDetailMapper.selectCurrencyList(id);
			List<TaskEntityDetailDTO> taskEntityDetailPOList = TaskEntityDetailConverter.INSTANCE.domain2dto(currencyTypeLists);

			if (currencyTypeLists != null) {
				List<TaskEntityDetailDTO> currencyTypeList = new ArrayList<TaskEntityDetailDTO>();
				for (TaskEntityDetailDTO currencyTypeList1 : taskEntityDetailPOList) {
					TaskEntityDetailDTO currencyTypeList2 = new TaskEntityDetailDTO();
					currencyTypeList2.setId(currencyTypeList1.getId());
					currencyTypeList2.setTaskNo(currencyTypeList1.getTaskNo());
					currencyTypeList2.setKey(currencyTypeList1.getKey());
					currencyTypeList2.setValue(currencyTypeList1.getValue());
					currencyTypeList2.setName(currencyTypeList1.getName());
					currencyTypeList.add(currencyTypeList2);
				}
				containerNoList2.setTaskEntityDetailDTOList(currencyTypeList);
			}
			conList.add(containerNoList2);
		}
		return conList;
	}

}
