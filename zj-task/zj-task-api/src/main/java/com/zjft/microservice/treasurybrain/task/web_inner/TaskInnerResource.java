package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.*;

import java.util.List;
import java.util.Map;

public interface TaskInnerResource {

	/**
	 * 插入任务表
	 * @param paramMap
	 * @return
	 */
	//已改
	int insertSelectiveByMap(Map<String, Object> paramMap);

	/**
	 * 查询下一可用任务编号
	 * @param clrCenterNo
	 * @param taskType
	 * @return
	 */
	String getNextLogicId(String clrCenterNo, int taskType);

	/**
	 * 任务单对应的金库编号
	 * @param taskNo
	 * @return
	 */
	String getClrCenterNo(String taskNo);

	/**
	 * 通过任务单编号查询网点入库的笼车编号
	 * @param taskNo
	 * @return
	 */
	String getShelfNo(String taskNo);

	/**
	 * 通过任务单编号查询任务表中信息  已改
	 * @param taskNo
	 * @return
	 */
	TaskTableDTO getByPrimaryKey(String taskNo);

	/** 已改
	 * 通过任务单编号更新任务表中信息
	 * @param paramMap taskNo必传
	 * @return
	 */
	int updateByPrimaryKeyMap(Map<String, Object> paramMap);

	/**
	 * 通过条件查询任务单
	 * @param paramMap  lineNo、status列表、planFinishDate  可选
	 * @return 任务单编号列表
	 */
	List<String> selectTaskNoByLineNo(Map<String, Object> paramMap);

	/**
	 * 通过条件查询任务单 已改
	 * @param paramMap    lineNo、status、planFinishDate、taskType  可选
	 * @return 任务单编号列表
	 */
	List<String> qryTaskNoByLineNo(Map<String, Object> paramMap);

	/**
	 * 通过任务单编号查询任务单状态
	 * @param taskNo
	 * @return
	 */
	Integer getStatus(String taskNo);

	/**
	 * 根据任务单编号批量更新状态
	 *
	 */
	int cashInStorage(List<String> taskNos, Integer status);

	/**
	 * 查询自己当天可执行的线路
	 * @param status
	 * @param planFinishDate
	 * @param opNo
	 * @return
	 */
	List<String> getTodayLine(String status, String planFinishDate, String opNo);

	/**
	 * 查询可执行的线路
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> qryLineNoList(Map<String, Object> paramMap);

	/**
	 * 已改
	 *
	 * 根据笼车编号查询对应的任务单编号，线路编号
	 *
	 * @param shelfNo 笼车编号
	 * @return StorageEntityTransfer
	 */
	List<StorageUseEntityTransferDTO> getLine(String shelfNo);

	/**
	 * 根据笼车编号查询对应的任务单编号列表
	 *
	 * @param shelfNo 笼车编号
	 * @return 任务单列表
	 */
	List<String> getTaskListByShelfNo(String shelfNo);

	/**
	 * 根据笼车编号查询对应的任务单编号列表
	 *
	 * @param shelfNo 笼车编号   taskType  任务类型
	 * @return 任务单编号列表
	 */
	List<String> getTaskListByShelfNoAndTaskType(String shelfNo, Integer taskType);

	/**
	 * 已改
	 *
	 * 查询满足条件的库存信息的结果数
	 *
	 * @param paramMap 金库编号 任务单编号 笼车编号
	 * @return 结果数
	 */
	int qryTotalRowStorage(Map<String, Object> paramMap);

	/**
	 * 已改
	 *
	 * 分页查询满足条件的库存信息
	 *
	 * @paramparamMap 金库编号 任务单编号 笼车编号
	 * @return 库存信息列表
	 */
	List<StorageUseEntityDTO> qryStorageEntityByPage(Map<String, Object> paramMap);

	/**
	 * 已改
	 *
	 * 根据任务单编号返回库存基本信息
	 *
	 * @param taskNo 任务单编号
	 * @return 库存基本信息
	 */
	StorageUseEntityDetailDTO getStorageInfo(String taskNo);

	/**
	 * 已改
	 *
	 * 根据线路编号查询笼车编号
	 *
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> qryShelfNoByLineNo(Map<String, Object> paramMap);

	/**
	 * 已改
	 *
	 * 根据线路编号查询笼车上的详细信息
	 *
	 * @param paramMap
	 * @return
	 */
	List<String> qryShelfDetailByShelfNo(Map<String, Object> paramMap);

	/**
	 * 查询当前任务状态
	 *
	 * @param taskNo
	 * @return
	 */
	Integer qryCurStatus(String taskNo);

	/**
	 * 查询流转任务个数
	 *
	 * @param paramMap
	 * @return
	 */
	int qryTotalRowTauro(Map<String, Object> paramMap);

	/**
	 * 分页查询流转任务
	 *
	 * @param paramMap
	 * @return
	 */
	List<TaskTauroDTO> qryDispatchByPage(Map<String, Object> paramMap);

	/**
	 * 查询任务类型
	 *
	 * @param taskNo
	 * @return
	 */
	Integer qryTaskType(String taskNo);

	/**
	 * 插入任务表
	 * @param paramMap
	 * @return
	 */
	int insertTaskLineByMap(Map<String, Object> paramMap);

	/**
	 * 查询任务单下的设备
	 *
	 * @param taskNo 任务单编号
	 * @return 设备信息
	 */
	String getDev(String taskNo);

	/**
	 * 根据任务单编号查询金额明细
	 * @param taskNo 任务编号
	 * @return
	 */
	List<TaskProductDTO>  qryTaskDetailList(String taskNo);

	/**
	 * 根据任务单编号查询箱子及箱子金额明细
	 * @param taskNo 任务编号
	 * @return
	 */
	List<TaskEntityDTO> qryEntityNoLists(String taskNo);

}
