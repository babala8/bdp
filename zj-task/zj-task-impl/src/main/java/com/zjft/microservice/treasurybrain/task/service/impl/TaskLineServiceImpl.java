package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.task.repository.TaskLineMapper;
import com.zjft.microservice.treasurybrain.task.service.TaskLineService;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskLineInnerResource;
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
public class TaskLineServiceImpl implements TaskLineService {
	@Resource
	private TaskLineMapper taskLineMapper;
	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return taskLineMapper.insertSelectiveByMap(paramMap);
	}

	@Override
	public int updateSelectiveByMap(Map<String, Object> paramMap) {
		return taskLineMapper.updateSelectiveByMap(paramMap);
	}

	@Override
	public int deleteByTaskNo(String taskNo){
		return taskLineMapper.deleteByTaskNo(taskNo);
	}
}
