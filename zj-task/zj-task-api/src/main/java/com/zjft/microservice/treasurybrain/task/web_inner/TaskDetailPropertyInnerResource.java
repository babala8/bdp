package com.zjft.microservice.treasurybrain.task.web_inner;

import java.util.Map;

public interface TaskDetailPropertyInnerResource {

	/** 已改
	 * 插入订单产品详细表
	 * @param paramMap
	 * @return
	 */
	int insertSelectiveByMap(Map<String, Object> paramMap);

	/** 已改
	 * 根据任务单编号删除订单产品详细表
	 * @param taskNo
	 * @return
	 */
	int deleteByTaskNo(String taskNo);
}
