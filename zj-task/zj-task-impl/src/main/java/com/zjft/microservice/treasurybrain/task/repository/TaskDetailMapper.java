package com.zjft.microservice.treasurybrain.task.repository;

import com.zjft.microservice.treasurybrain.task.domain.TaskDetail;
import com.zjft.microservice.treasurybrain.task.domain.TaskDetailKey;
import com.zjft.microservice.treasurybrain.task.domain.TaskDetailToExportDO;
import com.zjft.microservice.treasurybrain.task.dto.CustomerInfoDTO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskDetailMapper {
	int deleteByPrimaryKey(TaskDetailKey key);

	int insert(TaskDetail record);

	int insertSelective(TaskDetail record);

	int insertSelectiveByMap(Map<String, Object> paramMap);

	TaskDetail selectByPrimaryKey(TaskDetailKey key);

	int updateByPrimaryKeySelective(TaskDetail record);

	int updateByPrimaryKey(TaskDetail record);

	int updateByTaskNo(Map<String, Object> paramMap);

	int updateByPrimaryKeyMap(Map<String, Object> paramMap);

	List<Object> getSortNoList(String taskNo);

	List<TaskDetail> selectDetailWithSortNo(Map<String, Object> paramMap);

	List<TaskDetail> getDetailList(String taskNo);

	@Select("select tt.TASK_NO from (\n" +
			"select t.TASK_NO,ROWNUM rn from TASK_TABLE t left join TASK_ENTITY_TABLE TDT on t.TASK_NO = TDT.TASK_NO\n" +
			"where TDT.ENTITY_NO = #{customerNo, jdbcType=VARCHAR} and t.STATUS in (205,304,402,309)\n" +
			"order by t.TASK_NO desc )tt where tt.rn = 1")
	String qryPreviousTaskNo(String customerNo);

	@Select("select a.ENTITY_NO from TASK_ENTITY_TABLE a left join TASK_TABLE b on a.TASK_NO=b.TASK_NO " +
			"where a.TASK_NO= #{previousTaskNo, jdbcType=VARCHAR} and LEAF_FLAG = 1 and DIRECTION = 1 and b.CUSTOMER_NO=#{customerNo, jdbcType=VARCHAR}")
	List<String> qryPreviousContainByPreviousTaskNo(@Param("previousTaskNo") String previousTaskNo, @Param("customerNo") String customerNo);

	List<TaskDetailToExportDO> getDetailToExportList(String taskNo);

	/**
	 * 根据任务单编号查询任务单所有明细
	 *
	 * @param taskNo
	 * @return
	 */
	List<TaskDetail> qryByTaskNo(String taskNo);

	/**
	 * 根据任务单号删除
	 * @param taskNo
	 * @return
	 */
	int deleteByTaskNo(String taskNo);

	/**
	 * 查询任务单对应的设备台数
	 * @param taskNo
	 * @return
	 */
	int selectCustomerCount(String taskNo);

	List<TaskDetail> selectByTaskNo(String taskNo);

	/**
	 * 查出每一个taskNo下对应的 Customer（ATM机）的信息
	 * @param taskNo
	 * @return
	 */
	List<CustomerInfoDTO> qryCustomerInfo(String taskNo);

	TaskEntityPO selectCustomerNoList(Map<String, Object> paramMap);

	/**
	 * 查询任务单下未配钞设备数
	 *
	 * @param taskNo 任务单状态
	 * @return 未配钞设备数
	 */
	int qryDevNumToLoad(String taskNo);

	/**
	 * 查询任务单对应的已清点设备台数
	 * @param
	 * @return
	 */
	int selectCount(Map<String, Object> paramMap);

	/** 已改
	 * 查询任务明细中的总金额
	 * @param map    参数  taskNo   direction
	 * @return
	 */
	Integer selectSumAmount(Map<String ,Object> map);

}
