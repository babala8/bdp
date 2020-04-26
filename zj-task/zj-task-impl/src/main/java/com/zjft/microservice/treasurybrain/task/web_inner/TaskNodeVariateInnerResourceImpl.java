package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.TaskNodeVariateDTO;
import com.zjft.microservice.treasurybrain.task.service.TaskNodeVariateInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-07
 */
@Slf4j
@RestController
public class TaskNodeVariateInnerResourceImpl implements TaskNodeVariateInnerResource {
	@Resource
	private TaskNodeVariateInnerService taskNodeVariateInnerService;
	@Override
	public int insertBatch(List<TaskNodeVariateDTO> list) {
		return taskNodeVariateInnerService.insertBatch(list);
	}

	@Override
	public int insert(TaskNodeVariateDTO taskNodeVariatePO) {
		return taskNodeVariateInnerService.insert(taskNodeVariatePO);
	}

	@Override
	public void getJSONToInsert(String taskNodeNo, int taskType, String operateType, Object obj) {
		taskNodeVariateInnerService.getJSONToInsert(taskNodeNo,taskType,operateType,obj);
	}

	@Override
	public void getJSONToInsertBatch(List<Map<String, Object>> list) {
		taskNodeVariateInnerService.getJSONToInsertBatch(list);
	}
}
