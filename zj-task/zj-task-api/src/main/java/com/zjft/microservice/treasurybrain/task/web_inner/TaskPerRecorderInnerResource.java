package com.zjft.microservice.treasurybrain.task.web_inner;

import java.util.Map;

public interface TaskPerRecorderInnerResource {

	/**
	 * 插入任务执行记录表
	 * @param paramMap
	 * @return
	 */
	int insertByMap(Map<String,Object> paramMap);
}
