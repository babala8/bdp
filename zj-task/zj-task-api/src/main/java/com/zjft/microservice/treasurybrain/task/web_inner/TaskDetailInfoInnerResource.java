package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.CustomerInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskDetailTableDTO;

import java.util.List;
import java.util.Map;

public interface TaskDetailInfoInnerResource {


	/**
	 * 根据任务单号删除任务详细表
	 * @param taskNo
	 * @return
	 */
	int deleteByTaskNo(String taskNo);

	/**已改
	 * 插入任务详细表
	 * @param paramMap
	 * @return
	 */
	int insertSelectiveByMap(Map<String, Object> paramMap);

	/**
	 * 更新任务详细表
	 * @param paramMap taskNo与customerNo必传
	 * @return
	 */
	int updateByPrimaryKeyMap(Map<String, Object> paramMap);

	/**
	 * 根据任务单号和服务对象编号查看任务详细信息
	 * @param taskNo
	 * @param customerNo
	 * @return
	 */
	TaskDetailTableDTO selectByPrimaryKey(String taskNo, String customerNo);

	/**
	 * 查询任务单下未配钞设备数
	 *
	 * @param taskNo 任务单状态
	 * @return 未配钞设备数
	 */
	int qryDevNumToLoad(String taskNo);

	/**
	 * 查询任务单的个数
	 * @param
	 * @return
	 */
	int selectCount(Map<String, Object> paramMap);

	/**
	 * 查出每一个taskNo下对应的 Customer（ATM机）的信息
	 * @param taskNo
	 * @return
	 */
	List<CustomerInfoDTO> qryCustomerInfo(String taskNo);
}
