package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.task.domain.TaskDetail;
import com.zjft.microservice.treasurybrain.task.domain.TaskDetailKey;
import com.zjft.microservice.treasurybrain.task.dto.CustomerInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskDetailTableDTO;
import com.zjft.microservice.treasurybrain.task.mapstruct.TaskDetailConverter;
import com.zjft.microservice.treasurybrain.task.repository.TaskDetailMapper;
import com.zjft.microservice.treasurybrain.task.service.TaskDetailInfoInnerService;
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
public class TaskDetailInfoInnerServiceImpl implements TaskDetailInfoInnerService {
	@Resource
	private TaskDetailMapper taskDetailMapper;
	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskDetailMapper.deleteByTaskNo(taskNo);
	}

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskDetailMapper.insertSelectiveByMap(paramMap);
	}

	@Override
	public int updateByPrimaryKeyMap(Map<String, Object> paramMap) {
		return taskDetailMapper.updateByPrimaryKeyMap(paramMap);
	}

	@Override
	public TaskDetailTableDTO selectByPrimaryKey(String taskNo, String customerNo) {
		TaskDetailKey taskDetailKey = new TaskDetailKey();
		taskDetailKey.setTaskNo(taskNo);
		taskDetailKey.setCustomerNo(customerNo);
		TaskDetail taskDetail = taskDetailMapper.selectByPrimaryKey(taskDetailKey);
		TaskDetailTableDTO taskDetailTableDTO = TaskDetailConverter.INSTANCE.domain2dto(taskDetail);
		return taskDetailTableDTO;
	}

	@Override
	public int qryDevNumToLoad(String taskNo) {
		return taskDetailMapper.qryDevNumToLoad(taskNo);
	}

	@Override
	public int selectCount(Map<String, Object> paramMap) {
		return taskDetailMapper.selectCount(paramMap);
	}

	@Override
	public List<CustomerInfoDTO> qryCustomerInfo(String taskNo) {
		return taskDetailMapper.qryCustomerInfo(taskNo);
	}
}
