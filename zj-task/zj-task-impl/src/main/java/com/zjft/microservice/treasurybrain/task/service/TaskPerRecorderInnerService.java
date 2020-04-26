package com.zjft.microservice.treasurybrain.task.service;

import java.util.Map;

public interface TaskPerRecorderInnerService {

	/**
	 * 插入任务执行记录表
	 * @param paramMap
	 * @return
	 */
	int insertByMap(Map<String,Object> paramMap);
}
