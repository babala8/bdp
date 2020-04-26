package com.zjft.microservice.treasurybrain.task.repository;

import com.zjft.microservice.treasurybrain.task.dto.ContainerInfoDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteDTO;
import com.zjft.microservice.treasurybrain.task.dto.TaskEntityTableInfoDTO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskEntityMapper {
    int deleteByPrimaryKey(TaskEntityPO key);

	int deleteByTaskNo(String taskNo);

	int deleteByTaskNoAndEntityNo(@Param("taskNo") String taskNo, @Param("EntityNo") String EntityNo);

	int insert(TaskEntityPO record);

    int insertSelective(TaskEntityPO record);

    int insertSelectiveByMap(Map<String, Object> paramMap);

	TaskEntityPO selectByPrimaryKey(TaskEntityPO key);

    int updateByPrimaryKeySelective(TaskEntityPO record);

    int updateByPrimaryKey(TaskEntityPO record);

	List<TaskEntityPO> selectByTaskNo(String taskNo);

	int  selectContainerCount(String taskNo);

	/**
	 * 查找需要当日出库的容器信息
	 */
	List<TaskEntityPO> selectOutInfo(String note);

	/**
	 * 查找某容器的下级容器列表
	 */
	List<String> qryLowerContainterNoListByNo(@Param("containterNo") String containterNo, @Param("taskNo") String taskNo);

	/**
	 * 查找钞袋里回收的钞箱编号
	 */
	List<String> qryRecoverContainterNoListByNo(@Param("containterNo") String containterNo, @Param("taskNo") String taskNo);

	/**
	 * 通过containerNo查询最新任务物品对应信息
	 */
	TaskEntityPO qryByContainerNo(String entityNo);

	/**
	 * 通过containerNo和taskNo查询最新任务物品对应信息
	 */
	TaskEntityPO qryByTaskNoAndContainerNo(Map map);

	/**
	 * 通过containerNo找出所对应的钞袋的服务对象
	 */
	String qryNewCustomerNoBycontainerNo(@Param("entityNo") String entityNo, @Param("taskNo") String taskNo);

	/**
	 * 通过任务编号、钞袋编号查找所有钞箱
	 */
	List<String> selectCassetteNos(@Param("taskNo") String taskNo,@Param("entityNo") String entityNo);
	/**
	 * 根据任务单编号查询容器编号列表（经警接库）
	 */
	List<String> selectContainerNoByTaskNo(String taskNo);

	List<ContainerInfoDTO> qryContainerInfo(@Param("taskNo") String taskNo, @Param("customerNo") String customerNo);

	List<TaskEntityPO> selectContainerNoList(Map<String, Object> paramMap);

	/**
	 * 查询容器编号是否被任务单占用
	 */
	int selectCountByContainerNo(String containerNo);

	/**
	 * 插入或更新任务物品信息
	 */
	int insertOrUpdateByPrimaryKey(Map<String, Object> paramMap);

	List<TaskEntityTableInfoDTO> selectByCustomerNo(Map<String, Object> paramMap);

	int updateByTaskNoAndCustomerNo(Map<String, Object> paramMap);

	/**
	 *根据任务单编号找到对应的实物列表
	 * 【手持机扫描时只扫描最外层容器，故这里只返回最外层容器编号列表】
	 *
	 * @param taskNo 任务单编号
	 * @return 实物列表
	 */
	List<String> getGoodsByTaskNo(String taskNo);

	/**
	 * 查询任务单下的所有设备列表
	 *
	 * @param taskNo 任务单编号
	 * @return 设备信息列表
	 */
	List<String> getDevList(String taskNo);

	List<TaskEntityPO> selectByTaskNoAndCustomerNo(Map<String, Object> paramMap);

	/**
	 * 查询任务单下的容器个数
	 *
	 * @param taskNo 任务单编号
	 * @return
	 */
	int selectTaskNoCount(String taskNo);

	/**
	 * 查询任务单下的容器里钱的总金额
	 *
	 * @param taskNo 任务单编号
	 * @return
	 */
	double selectAmountByTaskNo(String taskNo);

	/** 已改
	 * 根据任务单号和容器号查询任务物品信息
	 * @param taskNo
	 * @return
	 */
	TaskEntityPO selectOneByTaskNo(@Param("taskNo") String taskNo,@Param("entityNo") String entityNo);

	/** 已改
	 * 统计任务单调入或调出总金额
	 * @param paramMap taskNo--任务编号  direction--调入调出方向
	 * @return
	 */
	Integer selectSumAmount(Map<String, Object> paramMap);

	/** 已改
	 * 统计任务单调入或调出箱子总个数
	 * @param paramMap taskNo--任务编号  direction--调入调出方向
	 * @return
	 */
	Integer selectSumCount(Map<String, Object> paramMap);

	int selectByTaskNoAndContainerNo(Map<String, Object> paramMap);

	List<StorageUseCassetteBagDTO> getCassetteBagList(@Param("taskNo")String taskNo, @Param("devNo")String devNo);

	List<StorageUseCassetteDTO> getCassetteList(@Param("taskNo")String taskNo, @Param("entityNo")String entityNo);

}
