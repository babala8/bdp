package com.zjft.microservice.treasurybrain.task.service;

import java.util.Map;

public interface TaskDetailPropertyInnerService {

	/**
	 * 插入订单产品详细表
	 * @param paramMap
	 * @return
	 */
	int insertSelectiveByMap(Map<String, Object> paramMap);

	int deleteByTaskNo(String taskNo);
}
