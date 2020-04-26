package com.zjft.microservice.treasurybrain.task.service;

import java.util.Map;

public interface TaskLineService {

	/**
	 * 插入任务线路表
	 * @param paramMap
	 * @return
	 */
	int insertSelectiveByMap(Map<String, Object> paramMap);

	/**
	 * 修改任务线路表
	 * @param paramMap
	 * @return
	 */
	int updateSelectiveByMap(Map<String, Object> paramMap);

	/**
	 * 根据任务单编号删除任务线路表
	 * @param taskNo
	 * @return
	 */
	int deleteByTaskNo(String taskNo);
}
