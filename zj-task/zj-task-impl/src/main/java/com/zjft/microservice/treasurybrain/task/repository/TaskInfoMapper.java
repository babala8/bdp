package com.zjft.microservice.treasurybrain.task.repository;

import com.zjft.microservice.treasurybrain.task.domain.TaskCountInfo;
import com.zjft.microservice.treasurybrain.task.domain.TaskInfo;
import com.zjft.microservice.treasurybrain.task.domain.TaskTableForName;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseEntityDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseEntityDetailDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseEntityTransferDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskTauroDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskInfoMapper {
    int deleteByPrimaryKey(String taskNo);

    int insert(TaskInfo record);

    int insertSelective(TaskInfo record);

    int insertSelectiveByMap(Map<String, Object> paramMap);

    //已改
	TaskInfo selectByPrimaryKey(String taskNo);

	TaskTableForName selectDetailByPrimaryKey(String taskNo);

	List<TaskTableForName>  selectDetailByTask(String taskNo);

	//已改
    int updateByPrimaryKeySelective(TaskInfo record);

    int updateByPrimaryKey(TaskInfo record);

	String selectByAddDate(@Param("clrCenterNo") String clrCenterNo, @Param("createDate") String createDate,@Param("taskType") int taskType);

	int updateByTaskNo(Map<String, Object> paramMap);

	int updateByPrimaryKeyMap(Map<String, Object> paramMap);

	int selectTotalRow(Map<String, Object> paramMap);

	List<Map<String, Object>> selectTask(Map<String, Object> paramMap);

	List<TaskInfo> selectOwnTask(Map<String, Object> paramMap);

	TaskInfo selectOwnTaskDetail(String taskNo);

	List<TaskInfo> qryTaskByLineNo(Map<String, Object> paramMap);

	String qryTaskByCustomerNo(Map<String, Object> paramMap);

	List<TaskInfo> qryTaskDetailByContainerNo(Map<String, Object> paramMap);

	List<Map<String, Object>> qryLineNoList(Map<String, Object> paramMap);

	List<Map<String, Object>> qryShelfNoByLineNo(Map<String, Object> paramMap);

	List<String> qryShelfDetailByShelfNo(Map<String, Object> paramMap);

	/**
	 *
	 * 查询列表中是否有不是该类型的任务单
	 *
	 * @param taskNos 任务单编号列表
	 * @param taskType 任务单类型
	 * @return
	 */
	List<String> isTaskOutOfType(@Param("taskNos") List<String> taskNos,@Param("taskType") int taskType);

	/**
	 *
	 * 任务过期
	 * 根据任务单类型状态还有计划完成时间修改状态为已过期
	 *
	 * @param paramMap outTimeStatus 过期状态 nowTime 当前时间 originStatus 过期前状态 taskType 任务单类型
	 * @return
	 */
	int taksTimeOut(Map<String,Object> paramMap);

	Integer selectTaskType (String taskNo);

	List<Map<String, Object>> selectBankNote(Map<String, Object> paramMap);

	/**
	 * 分页查询清点信息
	 * @param paramMap
	 * @return
	 */
	List<TaskInfo> qryTotalLoadCheck(Map<String, Object> paramMap);

	/**
	 * 查询清点信息个数
	 * @param paramMap
	 * @return
	 */
	int qryTotalRowLoadCheck(Map<String, Object> paramMap);

	/**
	 * 根据条件查询配钞信息个数
	 * @param paramMap
	 * @return
	 */
	int qryTotalRowLoadInfo(Map<String, Object> paramMap);

	/**
	 * 根据条件查询配钞信息updateByPrimaryKey
	 * @param paramMap
	 * @return
	 */
	List<TaskInfo> qryTotalLoadInfo(Map<String, Object> paramMap);

	int qryTotalRow(Map<String, Object> paramMap);

	List<TaskCountInfo> qryCountTaskListByPage(Map<String, Object> paramMap);

	/**
	 * 查找任务单对应金库编号
	 */
	String getClrCenterNo(String taskNo);

	/**
	 * 通过任务单编号查询网点入库的笼车编号
	 */
	String getShelfNo(String taskNo);

	/**
	 * 通过线路编号查询当日可执行任务单
	 */
	List<String> selectTaskNoByLineNo(Map<String,Object> paramMap);

	/**
	 * 通过线路编号查询当日可执行任务单  已改
	 */
	List<String> qryTaskNoByLineNo(Map<String, Object> paramMap);

	/**
	 * 根据编号查询任务单状态
	 *
	 * @param taskNo 任务单编号
	 * @return 任务单状态
	 */
	Integer getStatusByNo(String taskNo);

	/**
	 * 根据任务单编号更新状态
	 *
	 */
	int cashInStorage(@Param("list") List<String> taskNos, @Param("status") Integer status);

	/**
	 * 查询自己可执行的线路
	 */
	List<String> getTodayLine(@Param("tatus") String status, @Param("planFinishDate") String planFinishDate, @Param("opNo") String opNo);

	/**
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
	 * @param shelfNo 笼车编号  taskType  任务类型
	 * @return 任务单列表
	 */
	List<String> getTaskListByShelfNoAndTaskType(@Param("shelfNo") String shelfNo,@Param("taskType") Integer taskType);

	/**
	 * 查询满足条件的库存信息的结果数
	 *
	 * @param paramMap 金库编号 任务单编号 笼车编号
	 * @return 结果数
	 */
	int qryTotalRowStorage(Map<String, Object> paramMap);

	/**
	 * 分页查询满足条件的库存信息
	 *
	 * @param paramMap 金库编号 任务单编号 笼车编号
	 * @return 库存信息列表
	 */
	List<StorageUseEntityDTO> qryStorageEntityByPage(Map<String, Object> paramMap);

	/**
	 * 根据任务单编号返回库存基本信息
	 *
	 * @param taskNo 任务单编号
	 * @return 库存基本信息
	 */
	StorageUseEntityDetailDTO getStorageInfo(String taskNo);

	@Select("select STATUS from TASK_TABLE where TASK_NO=#{taskNo,jdbcType=VARCHAR}")
	Integer qryCurStatus(String taskNo);

	int qryTotalRowTauro(Map<String, Object> paramMap);

	List<TaskTauroDTO> qryDispatchByPage(Map<String, Object> paramMap);

	int insertTaskLineByMap(Map<String, Object> paramMap);

	String getDev(String taskNo);

}
