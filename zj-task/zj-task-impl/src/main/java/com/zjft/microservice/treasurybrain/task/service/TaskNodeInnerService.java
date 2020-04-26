package com.zjft.microservice.treasurybrain.task.service;

import com.zjft.microservice.treasurybrain.task.dto.TaskNodeDTO;

import java.util.List;

public interface TaskNodeInnerService {

	/**
	 * 插入任务节点信息
	 *
	 * @param taskNodeDTO 任务节点对象
	 * @return int
	 */
	int insert(TaskNodeDTO taskNodeDTO);

	/**
	 * 批量插入任务节点信息
	 *
	 * @param taskNodeDTOList 任务节点列表
	 * @return int
	 */
	int insertBatch( List<TaskNodeDTO> taskNodeDTOList);

	/**
	 * 根据任务编号查询下一可用任务节点编号
	 *
	 * @param taskNo 任务编号
	 * @return 最大任务节点编号
	 */
	String getNextTaskNodeNo(String taskNo);

	/**
	 * 根据任务编号查询最大任务节点编号
	 *
	 * @param taskNo 任务编号
	 * @return
	 */
	List<TaskNodeDTO> qryTaskNode(String taskNo);
}
