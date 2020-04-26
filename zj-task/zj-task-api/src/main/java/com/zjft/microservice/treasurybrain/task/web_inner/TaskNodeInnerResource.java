package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.TaskNodeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskNodeInnerResource {

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
	int insertBatch(List<TaskNodeDTO> taskNodeDTOList);

	/**
	 * 根据任务单编号计算出可以使用的下一节点编号
	 * @param taskNo 任务单编号
	 * @return 任务下一节点编号（ 任务节点编号 = 任务编号 + 01（递增） ）
	 */
	String getNextTaskNodeNo(String taskNo);

	/**
	 * 根据任务编号查询最大任务节点编号
	 *
	 * @param taskNo 任务编号
	 * @return
	 */
	List<TaskNodeDTO> qryTaskNode(@Param("taskNo") String taskNo);
}
