package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.*;
import com.zjft.microservice.treasurybrain.task.service.TaskEntityService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class TaskEntityInnerResourceImpl implements TaskEntityInnerResource {

	@Resource
	private TaskEntityService taskEntityService;
	@Override
	public List<TaskEntityDTO> selectOutInfo(String note) {
		return taskEntityService.selectOutInfo(note);
	}

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskEntityService.insertSelectiveByMap(paramMap);
	}

	@Override
	public List<TaskEntityDTO> selectByTaskNo(String taskNo) {
		return taskEntityService.selectByTaskNo(taskNo);
	}

	@Override
	public List<TaskEntityDTO> selectByTaskNoAndCustomerNo(Map<String, Object> paramMap) {
		return taskEntityService.selectByTaskNoAndCustomerNo(paramMap);
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskEntityService.deleteByTaskNo(taskNo);
	}

	@Override
	public int deleteByTaskNoAndEntityNo(String taskNo, String EntityNo) {
		return taskEntityService.deleteByTaskNoAndEntityNo(taskNo,EntityNo);
	}

	@Override
	public List<String> qryLowerContainterNoListByNo(String containterNo, String taskNo) {
		return taskEntityService.qryLowerContainterNoListByNo(containterNo,taskNo);
	}

	@Override
	public List<String> qryRecoverContainterNoListByNo(String containterNo, String taskNo) {
		return taskEntityService.qryRecoverContainterNoListByNo(containterNo,taskNo);
	}

	@Override
	public TaskEntityDTO qryByContainerNo(String entityNo) {
		return taskEntityService.qryByContainerNo(entityNo);
	}

	@Override
	public TaskEntityDTO qryByTaskNoAndContainerNo(Map map) {
		return taskEntityService.qryByTaskNoAndContainerNo(map);
	}

	@Override
	public String qryNewCustomerNoBycontainerNo(String entityNo, String taskNo) {
		return taskEntityService.qryNewCustomerNoBycontainerNo(entityNo,taskNo);
	}

	@Override
	public List<String> selectCassetteNos(String taskNo, String containerNo) {
		return taskEntityService.selectCassetteNos(taskNo,containerNo);
	}

	@Override
	public List<String> selectContainerNoByTaskNo(String taskNo) {
		return taskEntityService.selectContainerNoByTaskNo(taskNo);
	}

	@Override
	public int selectCountByContainerNo(String containerNo) {
		return taskEntityService.selectCountByContainerNo(containerNo);
	}

	@Override
	public int insertOrUpdateByPrimaryKey(Map<String, Object> paramMap) {
		return taskEntityService.insertOrUpdateByPrimaryKey(paramMap);
	}

	@Override
	public List<TaskEntityTableInfoDTO> selectByCustomerNo(Map<String, Object> paramMap) {
		return taskEntityService.selectByCustomerNo(paramMap);
	}

	@Override
	public int updateByTaskNoAndCustomerNo(Map<String, Object> paramMap) {
		return taskEntityService.updateByTaskNoAndCustomerNo(paramMap);
	}

	@Override
	public List<String> getGoodsByTaskNo(String taskNo) {
		return taskEntityService.getGoodsByTaskNo(taskNo);
	}

	@Override
	public List<String> getDevList(String taskNo) {
		return taskEntityService.getDevList(taskNo);
	}

	@Override
	public List<ContainerInfoDTO> qryContainerInfo(String taskNo, String customerNo) {
		return taskEntityService.qryContainerInfo(taskNo,customerNo);
	}

	@Override
	public TaskEntityDTO selectOneByTaskNo(String taskNo, String entityNo) {
		return taskEntityService.selectOneByTaskNo(taskNo,entityNo);
	}

	@Override
	public int selectByTaskNoAndContainerNo(Map<String, Object> paramMap) {
		return taskEntityService.selectByTaskNoAndContainerNo(paramMap);
	}

	@Override
	public List<StorageUseCassetteBagDTO> getCassetteBagList(String taskNo, String devNo) {
		return taskEntityService.getCassetteBagList(taskNo,devNo);
	}

	@Override
	public List<StorageUseCassetteDTO> getCassetteList(String taskNo, String entityNo) {
		return taskEntityService.getCassetteList(taskNo,entityNo);
	}
}
