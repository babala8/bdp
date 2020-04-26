package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.TaskNodeVariateDTO;

import java.util.List;
import java.util.Map;

public interface TaskNodeVariateInnerResource {

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int insertBatch(List<TaskNodeVariateDTO> list);

	/**
	 * 插入
	 * @param taskNodeVariatePO
	 * @return
	 */
	int insert(TaskNodeVariateDTO taskNodeVariatePO);

	/**
	 * 查询json模板并匹配信息插入
	 * @param taskNodeNo
	 * @param taskType
	 * @param operateType
	 * @param obj
	 */
	void getJSONToInsert(String taskNodeNo, int taskType, String operateType, Object obj);

	/**
	 * 查询json模板并匹配信息批量插入
	 * @param list
	 */
	void getJSONToInsertBatch(List<Map<String,Object>> list);
}
