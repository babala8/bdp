package com.zjft.microservice.treasurybrain.task.service.impl;

import com.zjft.microservice.treasurybrain.task.repository.TaskPerRecorderMapper;
import com.zjft.microservice.treasurybrain.task.service.TaskPerRecorderInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-06
 */
@Slf4j
@Service
public class TaskPerRecorderInnerServiceImpl implements TaskPerRecorderInnerService {

	@Resource
	private TaskPerRecorderMapper taskPerRecorderMapper;
	@Override
	public int insertByMap(Map<String, Object> paramMap) {
		return taskPerRecorderMapper.insertByMap(paramMap);
	}
}
