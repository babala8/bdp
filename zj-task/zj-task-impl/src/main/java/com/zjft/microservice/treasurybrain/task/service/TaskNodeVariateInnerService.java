package com.zjft.microservice.treasurybrain.task.service;

import com.zjft.microservice.treasurybrain.task.dto.TaskNodeVariateDTO;

import java.util.List;
import java.util.Map;

public interface TaskNodeVariateInnerService {

	int insertBatch(List<TaskNodeVariateDTO> list);

	int insert(TaskNodeVariateDTO taskNodeVariatePO);

	void getJSONToInsert(String taskNodeNo, int taskType, String operateType, Object obj);

	void getJSONToInsertBatch(List<Map<String,Object>> list);
}
