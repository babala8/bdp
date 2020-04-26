package com.zjft.microservice.treasurybrain.task.service;

import com.zjft.microservice.treasurybrain.task.dto.CustomerInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskDetailTableDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-02
 */
public interface TaskDetailInfoInnerService {


	/**
	 *
	 * @param taskNo
	 * @return
	 */
	int deleteByTaskNo(String taskNo);

	/**
	 *
	 * @param paramMap
	 * @return
	 */
	int insertSelectiveByMap(Map<String, Object> paramMap);

	/**
	 *
	 * @param paramMap
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
