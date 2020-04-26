package com.zjft.microservice.treasurybrain.task.repository;

import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDTO;
import com.zjft.microservice.treasurybrain.task.dto.StorageUseCassetteBagDetailDTO;
import com.zjft.microservice.treasurybrain.task.po.TaskEntityDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface TaskEntityDetailMapper {
    int deleteByPrimaryKey(String id);

	int deleteByTaskNo(String taskNo);

	int deleteByTaskNoAndContainerNo(Map<String, Object> paramMap);

    int insert(TaskEntityDetailPO record);

    int insertSelective(TaskEntityDetailPO record);

	int insertSelectiveByMap(Map<String, Object> paramMap);

    List<TaskEntityDetailPO> selectByPrimaryKey(String id);

    List<TaskEntityDetailPO> selectByTaskNo(String taskNo);

    int updateByPrimaryKeySelective(TaskEntityDetailPO record);

	int updateByID(Map<String, Object> hashMap);

    int updateByPrimaryKey(TaskEntityDetailPO record);

	BigDecimal selectSumAmount(String taskNo);

	TaskEntityDetailPO selectByNo(@Param("taskNo") String taskNo, @Param("containerNo") String containerNo);

	int selectContainerTyoe(Map<String, Object> paramMap);

	int selectContainerNo(Map<String, Object> paramMap);

	List<TaskEntityDetailPO> selectCurrencyList(String id);

	int selectByTaskNoAndContainerNo(Map<String, Object> paramMap);

	/**
	 * 查询设备下的钞袋信息
	 *
	 * @param taskNo 任务单编号
	 * @param devNo 设备编号
	 * @return 钞袋信息列表
	 */
	List<StorageUseCassetteBagDTO> getCassetteBagList(@Param("taskNo")String taskNo, @Param("devNo")String devNo);

	/**
	 * 查询钞袋下的钞箱信息
	 *
	 * @param id id
	 * @return 钞箱信息列表
	 */
	List<StorageUseCassetteBagDetailDTO> getCassetteDetail(@Param("id")String id);
}
