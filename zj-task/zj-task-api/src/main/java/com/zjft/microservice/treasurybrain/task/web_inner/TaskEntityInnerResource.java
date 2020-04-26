package com.zjft.microservice.treasurybrain.task.web_inner;

import com.zjft.microservice.treasurybrain.task.dto.*;

import java.util.List;
import java.util.Map;

public interface TaskEntityInnerResource {

	/**已改
	 * 查找需要当日出库的容器信息
	 *
	 * @param note 当天日期  YYYY-MM-DD
	 * @return
	 */
	List<TaskEntityDTO> selectOutInfo(String note);

	/**
	 * 已改
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
	 * 根据任务单号删除任务物品表
	 *
	 * @param taskNo
	 * @return
	 */
	int deleteByTaskNo(String taskNo);

	/**
	 * 已改过
	 * <p>
	 * 根据任务单号和服务对象编号删除任务物品表
	 *
	 * @param taskNo
	 * @param EntityNo
	 * @return
	 */
	int deleteByTaskNoAndEntityNo(String taskNo, String EntityNo);

	/**
	 * 已改
	 * 查找某容器的下级容器列表
	 *
	 * @param containterNo
	 * @param taskNo
	 * @return
	 */
	List<String> qryLowerContainterNoListByNo(String containterNo, String taskNo);

	/**
	 * 查找钞袋里回收的钞箱编号
	 *
	 * @param containterNo
	 * @param taskNo
	 * @return
	 */
	List<String> qryRecoverContainterNoListByNo(String containterNo, String taskNo);

	/**
	 * 已改
	 * 通过containerNo查询最新任务物品对应信息
	 *
	 * @param containerNo
	 * @return
	 */
	TaskEntityDTO qryByContainerNo(String containerNo);

	/**
	 * 已改
	 * 通过containerNo和taskNo查询最新任务物品对应信息
	 *
	 * @param map
	 * @return
	 */
	TaskEntityDTO qryByTaskNoAndContainerNo(Map map);

	/**
	 * 已改
	 * 通过entityNo找出所对应的钞袋的服务对象
	 *
	 * @param entityNo
	 * @param taskNo
	 * @return
	 */
	String qryNewCustomerNoBycontainerNo(String entityNo, String taskNo);

	/**
	 * 通过任务编号、钞袋编号查找所有钞箱
	 *
	 * @param taskNo
	 * @param containerNo
	 * @return
	 */
	List<String> selectCassetteNos(String taskNo, String containerNo);

	/**
	 * 已改
	 * 根据任务单编号查询容器编号列表（经警接库）
	 *
	 * @param taskNo
	 * @return
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
	 * 已改
	 * <p>
	 * 插入或更新任务物品信息
	 */
	int insertOrUpdateByPrimaryKey(Map<String, Object> paramMap);

	/**
	 * 已改
	 * <p>
	 * 通过服务对象编号查询任务物品信息
	 */
	List<TaskEntityTableInfoDTO> selectByCustomerNo(Map<String, Object> paramMap);

	/**
	 * 根据任务单号和设备号更新
	 */
	int updateByTaskNoAndCustomerNo(Map<String, Object> paramMap);

	/**
	 * 已改
	 * <p>
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

	/**
	 * 已改
	 * 根据任务单号和容器号查询任务物品信息
	 *
	 * @param taskNo
	 * @return
	 */
	TaskEntityDTO selectOneByTaskNo(String taskNo, String entityNo);


	/**
	 * 已改过
	 * <p>
	 * 根据任务单号与容器编号查询总个数
	 *
	 * @param paramMap taskNo与containerNo必传
	 * @return
	 */
	int selectByTaskNoAndContainerNo(Map<String, Object> paramMap);

	/**
	 * 已改
	 * <p>
	 * 查询设备下的钞袋信息
	 *
	 * @param taskNo 任务编号
	 * @param devNo  设备编号
	 * @return 钞袋信息列表
	 */
	List<StorageUseCassetteBagDTO> getCassetteBagList(String taskNo, String devNo);


	/**
	 * 新增
	 * <p>
	 * 查询钞袋下的钞箱
	 *
	 * @param taskNo   任务编号
	 * @param entityNo 钞袋编号
	 * @return 钞袋信息列表
	 */
	List<StorageUseCassetteDTO> getCassetteList(String taskNo, String entityNo);
}
