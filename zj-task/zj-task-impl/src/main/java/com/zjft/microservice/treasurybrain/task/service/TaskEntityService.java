package com.zjft.microservice.treasurybrain.task.service;

import com.zjft.microservice.treasurybrain.task.dto.*;

import java.util.List;
import java.util.Map;

public interface TaskEntityService {


	List<TaskEntityDTO> selectOutInfo(String note);

	/**
	 * 插入任务物品表
	 *
	 * @param paramMap
	 * @return
	 */
	int insertSelectiveByMap(Map<String, Object> paramMap);

	/**
	 * 根据任务单号查询任务物品信息
	 *
	 * @param taskNo
	 * @return
	 */
	List<TaskEntityDTO> selectByTaskNo(String taskNo);

	/**
	 * 根据任务单号查询任务物品信息
	 *
	 * @param paramMap
	 * @return
	 */
	List<TaskEntityDTO> selectByTaskNoAndCustomerNo(Map<String, Object> paramMap);

	/**
	 * 根据任务编号删除任务物品表
	 *
	 * @param taskNo
	 * @return
	 */
	int deleteByTaskNo(String taskNo);


	/**
	 * 查找某容器的下级容器列表
	 */
	List<String> qryLowerContainterNoListByNo(String containterNo, String taskNo);

	/**
	 * 查找钞袋里回收的钞箱编号
	 */
	List<String> qryRecoverContainterNoListByNo(String containterNo, String taskNo);

	/**
	 * 通过containerNo查询最新任务物品对应信息
	 */
	TaskEntityDTO qryByContainerNo(String containerNo);

	/**
	 * 通过containerNo和taskNo查询最新任务物品对应信息
	 */
	TaskEntityDTO qryByTaskNoAndContainerNo(Map map);

	/**
	 * 通过containerNo找出所对应的钞袋的服务对象
	 */
	String qryNewCustomerNoBycontainerNo(String containerNo, String taskNo);

	/**
	 * 通过任务编号、钞袋编号查找所有钞箱
	 */
	List<String> selectCassetteNos(String taskNo, String containerNo);

	/**
	 * 根据任务单编号查询容器编号列表（经警接库）
	 */
	List<String> selectContainerNoByTaskNo(String taskNo);

	/**
	 * 查询容器编号是否被任务单占用
	 *
	 * @param containerNo
	 * @return
	 */
	int selectCountByContainerNo(String containerNo);

	/**
	 * 插入或更新任务物品信息
	 */
	int insertOrUpdateByPrimaryKey(Map<String, Object> paramMap);

	List<TaskEntityTableInfoDTO> selectByCustomerNo(Map<String, Object> paramMap);

	/**
	 * 根据任务单号和设备号更新
	 */
	int updateByTaskNoAndCustomerNo(Map<String, Object> paramMap);

	/**
	 * 根据任务单编号找到对应的实物列表
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

	/**
	 * 查询任务物品信息
	 *
	 * @param taskNo     任务单编号
	 * @param customerNo 服务对象编号
	 * @return 任务物品信息列表
	 */
	List<ContainerInfoDTO> qryContainerInfo(String taskNo, String customerNo);

	int deleteByTaskNoAndEntityNo(String taskNo, String EntityNo);

	TaskEntityDTO selectOneByTaskNo(String taskNo, String entityNo);

	int selectByTaskNoAndContainerNo(Map<String, Object> paramMap);

	List<StorageUseCassetteBagDTO> getCassetteBagList(String taskNo, String devNo);

	List<StorageUseCassetteDTO> getCassetteList(String taskNo, String entityNo);
}
