package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.mapstruct.TaskEntityDetailConverter;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityDetailPO;
import com.zjft.microservice.treasurybrain.task.repository.TaskEntityDetailMapper;
import com.zjft.microservice.treasurybrain.task.service.TaskEntityDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-02
 */
@Slf4j
@Service
public class TaskEntityDetailServiceImpl implements TaskEntityDetailService {

	@Resource
	private TaskEntityDetailMapper taskEntityDetailMapper;

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskEntityDetailMapper.insertSelectiveByMap(paramMap);
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskEntityDetailMapper.deleteByTaskNo(taskNo);
	}

	@Override
	public int deleteByTaskNoAndContainerNo(Map<String, Object> paramMap) {
		return taskEntityDetailMapper.deleteByTaskNoAndContainerNo(paramMap);
	}

	@Override
	public List<TaskEntityDetailDTO> selectByTaskNo(String taskNo) {
		List<TaskEntityDetailPO> taskEntityDetailPOS = taskEntityDetailMapper.selectByTaskNo(taskNo);
		List<TaskEntityDetailDTO> taskEntityDetailDTOS = TaskEntityDetailConverter.INSTANCE.domain2dto(taskEntityDetailPOS);
		return taskEntityDetailDTOS;
	}

	@Override
	public TaskEntityDetailDTO selectOneByTaskNo(String taskNo, String entityNo) {
		TaskEntityDetailPO taskEntityDetailPO = taskEntityDetailMapper.selectByNo(taskNo, entityNo);
		TaskEntityDetailDTO taskEntityDetailDTO = TaskEntityDetailConverter.INSTANCE.domain2dto(taskEntityDetailPO);
		return taskEntityDetailDTO;
	}

	@Override
	public int selectByTaskNoAndContainerNo(Map<String, Object> paramMap) {
		return taskEntityDetailMapper.selectByTaskNoAndContainerNo(paramMap);
	}

	@Override
	public List<StorageUseCassetteBagDTO> getCassetteBagList(String taskNo, String devNo) {
		return taskEntityDetailMapper.getCassetteBagList(taskNo,devNo);
	}

	@Override
	public List<StorageUseCassetteBagDetailDTO> getCassetteDetail(String id) {
		return taskEntityDetailMapper.getCassetteDetail(id);
	}
}
