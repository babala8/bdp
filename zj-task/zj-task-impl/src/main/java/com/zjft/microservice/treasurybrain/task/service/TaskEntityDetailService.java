package com.zjft.microservice.treasurybrain.task.service;

import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TaskEntityDetailService {

	/**
	 * 插入任务物品详细表
	 * @param paramMap
	 * @return
	 */
	int insertSelectiveByMap(Map<String, Object> paramMap);

	/**
	 * 根据任务编号删除任务物品详细表
	 * @param taskNo
	 * @return
	 */
	int deleteByTaskNo(String taskNo);

	/**
	 * 根据任务单号和服务对象编号删除任务物品详细表
	 * @param paramMap
	 * @return
	 */
	int deleteByTaskNoAndContainerNo(Map<String, Object> paramMap);

	/**
	 * 根据任务单号查询任务物品信息
	 * @param taskNo
	 * @return
	 */
	List<TaskEntityDetailDTO> selectByTaskNo(String taskNo);

	/**
	 * 根据任务单号查询第一个任务物品信息
	 * @param taskNo
	 * @return
	 */
	TaskEntityDetailDTO selectOneByTaskNo(String taskNo, String entityNo);

	int selectByTaskNoAndContainerNo(Map<String, Object> paramMap);

	/**
	 * 查询设备下的钞袋信息
	 *
	 * @param taskNo 任务编号
	 * @param devNo 设备编号
	 * @return 钞袋信息列表
	 */
	List<StorageUseCassetteBagDTO> getCassetteBagList(String taskNo, String devNo);

	/**
	 * 查询钞袋下的钞箱详情信息
	 *
	 * @param id id
	 * @return 钞箱信息列表T
	 */
	List<StorageUseCassetteBagDetailDTO> getCassetteDetail(@Param("id")String id);
}
