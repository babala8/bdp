package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CountTaskInfoDO;
import com.zjft.microservice.treasurybrain.channelcenter.domain.DevCountDO;
import com.zjft.microservice.treasurybrain.channelcenter.domain.TaskCountInfo;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.channelcenter.po.DevCountPO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DevCountMapper {

	/**
	 * 分页查询设备信息列表
	 * @param paramMap
	 */
	List<DevCountDO> queryDevCountList(Map<String, Object> paramMap);

	/**
	 * 查询符合查询条件的设备总数
	 * @param paramMap
	 */
	int qryTotalRowDevCount(Map<String, Object> paramMap);

	/**
	 * @Description 查询设备编号是否存在
	 * @Param devNo
	 */
	int queryByDevNo(@Param("devNo") String devNo);

	/**
	 * @Description 新增清分机信息
	 * @Param
	 */
	int insert(DevCountPO devCountPO);

	/**
	 * @Description 修改清分机信息
	 * @Param
	 */
	int update(DevCountPO devCountPO);

	/**
	 * @Description 删除清分机信息
	 * @Param
	 */
	int delDevCountByNo(String devNo);


	/**
	 * @Description 查询清分机状态监控信息
	 * @Param
	 */
	List<DevCountDO> queryDevConMonitoring(Map<String, Object> paramMap);

	List<CountTaskInfoDO> selectCountTaskInfo(@Param("devNo") String devNo,@Param("countStatus") int countStatus);

	List<DevCountDO> qryCountTaskNum();

	/**
	 * @Description 查询清分任务是否存在
	 * @Param
	 */
	CountTaskInfoDO qryCountTaskInfoBytaskNo(@Param("taskNo") String taskNo);

	/**
	 * @Description 分配清分机
	 * @Param
	 */
	int insertCountTask(CountTaskInfoDO countTaskInfoDO);

	List<DevCountDO> qryCountDevList(Map<String, Object> paramMap);

	List<TaskCountInfo> selectByDevNo(String devNo);


}
