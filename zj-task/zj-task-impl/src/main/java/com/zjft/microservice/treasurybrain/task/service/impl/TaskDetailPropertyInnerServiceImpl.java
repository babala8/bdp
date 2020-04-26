package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.task.repository.TaskDetailPropertyMapper;
import com.zjft.microservice.treasurybrain.task.service.TaskDetailPropertyInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-02-25
 */
@Slf4j
@Service
public class TaskDetailPropertyInnerServiceImpl implements TaskDetailPropertyInnerService {

	@Resource
	private TaskDetailPropertyMapper taskDetailPropertyMapper;

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskDetailPropertyMapper.insertSelectiveByMap(paramMap);
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskDetailPropertyMapper.deleteByTaskNo(taskNo);
	}
}
