package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.mapstruct.TaskEntityConverter;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;
import com.zjft.microservice.treasurybrain.task.repository.TaskEntityMapper;
import com.zjft.microservice.treasurybrain.task.service.TaskEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-02
 */
@Slf4j
@Service
public class TaskEntityServiceImpl implements TaskEntityService {

	@Resource
	private TaskEntityMapper taskEntityMapper;
	@Override
	public List<TaskEntityDTO> selectOutInfo(String note) {
		List<TaskEntityPO> taskEntityPOS = taskEntityMapper.selectOutInfo(note);
		List<TaskEntityDTO> list = new ArrayList<>();
		TaskEntityDTO  taskEntityTableDTO = null;
		for (TaskEntityPO taskEntityPO : taskEntityPOS ) {
			taskEntityTableDTO = new TaskEntityDTO();
			taskEntityTableDTO.setTaskNo(taskEntityPO.getTaskNo());
			taskEntityTableDTO.setEntityNo(taskEntityPO.getEntityNo());
			taskEntityTableDTO.setProductNo(taskEntityPO.getProductNo());
			taskEntityTableDTO.setParentEntity(taskEntityPO.getParentEntity());
			taskEntityTableDTO.setAmount(taskEntityPO.getAmount());
			taskEntityTableDTO.setLeafFlag(taskEntityPO.getLeafFlag());
			taskEntityTableDTO.setDirection(taskEntityPO.getDirection());
			taskEntityTableDTO.setNote(taskEntityPO.getNote());
			taskEntityTableDTO.setDepositType(taskEntityPO.getDepositType());
			taskEntityTableDTO.setCustomerNo(taskEntityPO.getCustomerNo());
			list.add(taskEntityTableDTO);
		}
		return list;
	}

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskEntityMapper.insertSelectiveByMap(paramMap);
	}

	@Override
	public List<TaskEntityDTO> selectByTaskNo(String taskNo) {
		List<TaskEntityPO> taskEntityPOS = taskEntityMapper.selectByTaskNo(taskNo);
		List<TaskEntityDTO> taskEntityDTOList = TaskEntityConverter.INSTANCE.domain2dto(taskEntityPOS);
		return taskEntityDTOList;
	}

	@Override
	public List<TaskEntityDTO> selectByTaskNoAndCustomerNo(Map<String, Object> paramMap) {
		List<TaskEntityPO> taskEntityPOS = taskEntityMapper.selectByTaskNoAndCustomerNo(paramMap);
		List<TaskEntityDTO> taskEntityDTOList = TaskEntityConverter.INSTANCE.domain2dto(taskEntityPOS);
		return taskEntityDTOList;
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskEntityMapper.deleteByTaskNo(taskNo);
	}

	@Override
	public int deleteByTaskNoAndEntityNo(String taskNo, String EntityNo) {
		return taskEntityMapper.deleteByTaskNoAndEntityNo(taskNo,EntityNo);
	}

	@Override
	public List<String> qryLowerContainterNoListByNo(String containterNo, String taskNo) {
		return taskEntityMapper.qryLowerContainterNoListByNo(containterNo,taskNo);
	}

	@Override
	public List<String> qryRecoverContainterNoListByNo(String containterNo, String taskNo) {
		return taskEntityMapper.qryRecoverContainterNoListByNo(containterNo,taskNo);
	}

	@Override
	public TaskEntityDTO qryByContainerNo(String entityNo) {
		TaskEntityPO taskEntityPO = taskEntityMapper.qryByContainerNo(entityNo);
		TaskEntityDTO taskEntityDTO = TaskEntityConverter.INSTANCE.domain2dto(taskEntityPO);
		return taskEntityDTO;
	}

	@Override
	public TaskEntityDTO qryByTaskNoAndContainerNo(Map map) {
		TaskEntityPO taskEntityPO = taskEntityMapper.qryByTaskNoAndContainerNo(map);
		TaskEntityDTO taskEntityDTO = TaskEntityConverter.INSTANCE.domain2dto(taskEntityPO);
		return taskEntityDTO;
	}

	@Override
	public String qryNewCustomerNoBycontainerNo(String entityNo, String taskNo) {
		return taskEntityMapper.qryNewCustomerNoBycontainerNo(entityNo,taskNo);
	}

	@Override
	public List<String> selectCassetteNos(String taskNo, String containerNo) {
		return taskEntityMapper.selectCassetteNos(taskNo,containerNo);
	}

	@Override
	public List<String> selectContainerNoByTaskNo(String taskNo) {
		return taskEntityMapper.selectContainerNoByTaskNo(taskNo);
	}

	@Override
	public int selectCountByContainerNo(String containerNo) {
		return taskEntityMapper.selectCountByContainerNo(containerNo);
	}

	@Override
	public int insertOrUpdateByPrimaryKey(Map<String, Object> paramMap) {
		return taskEntityMapper.insertOrUpdateByPrimaryKey(paramMap);
	}

	@Override
	public List<TaskEntityTableInfoDTO> selectByCustomerNo(Map<String, Object> paramMap) {
		return taskEntityMapper.selectByCustomerNo(paramMap);
	}

	@Override
	public int updateByTaskNoAndCustomerNo(Map<String, Object> paramMap) {
		return taskEntityMapper.updateByTaskNoAndCustomerNo(paramMap);
	}

	@Override
	public List<String> getGoodsByTaskNo(String taskNo) {
		return taskEntityMapper.getGoodsByTaskNo(taskNo);
	}

	@Override
	public List<String> getDevList(String taskNo) {
		return taskEntityMapper.getDevList(taskNo);
	}

	@Override
	public List<ContainerInfoDTO> qryContainerInfo(String taskNo, String customerNo) {
		return taskEntityMapper.qryContainerInfo(taskNo,customerNo);
	}

	@Override
	public TaskEntityDTO selectOneByTaskNo(String taskNo, String entityNo) {
		TaskEntityPO taskEntityPO = taskEntityMapper.selectOneByTaskNo(taskNo, entityNo);
		TaskEntityDTO taskEntityDTO = TaskEntityConverter.INSTANCE.domain2dto(taskEntityPO);
		return taskEntityDTO;

	}

	@Override
	public int selectByTaskNoAndContainerNo(Map<String, Object> paramMap) {
		return taskEntityMapper.selectByTaskNoAndContainerNo(paramMap);
	}

	@Override
	public List<StorageUseCassetteBagDTO> getCassetteBagList(String taskNo, String devNo) {
		return taskEntityMapper.getCassetteBagList(taskNo,devNo);
	}

	@Override
	public List<StorageUseCassetteDTO> getCassetteList(String taskNo, String entityNo) {
		return taskEntityMapper.getCassetteList(taskNo,entityNo);
	}
}
