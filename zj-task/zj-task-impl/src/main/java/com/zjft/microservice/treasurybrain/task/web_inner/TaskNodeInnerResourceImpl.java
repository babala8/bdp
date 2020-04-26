package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.TaskNodeDTO;
import com.zjft.microservice.treasurybrain.task.service.TaskNodeInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 韩通
 * @since 2020-01-06
 */
@Slf4j
@RestController
public class TaskNodeInnerResourceImpl implements TaskNodeInnerResource {
	@Resource
	private TaskNodeInnerService taskNodeInnerService;
	@Override
	public int insert(TaskNodeDTO taskNodeDTO) {
		return taskNodeInnerService.insert(taskNodeDTO);
	}

	@Override
	public int insertBatch(List<TaskNodeDTO> taskNodeDTOList) {
		return taskNodeInnerService.insertBatch(taskNodeDTOList);
	}

	@Override
	public String getNextTaskNodeNo(String taskNo) {
		return taskNodeInnerService.getNextTaskNodeNo(taskNo);
	}

	@Override
	public List<TaskNodeDTO> qryTaskNode(String taskNo) {
		return taskNodeInnerService.qryTaskNode(taskNo);
	}
}
