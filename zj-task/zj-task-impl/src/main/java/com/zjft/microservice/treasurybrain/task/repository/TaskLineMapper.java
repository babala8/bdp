package com.zjft.microservice.treasurybrain.task.repository;

import com.zjft.microservice.treasurybrain.task.po.TaskLinePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TaskLineMapper {
    int insert(TaskLinePO record);

	int insertSelective(TaskLinePO record);

	/**
	 * 插入任务线路表
	 * @param paramMap
	 * @return
	 */
	int insertSelectiveByMap(Map<String, Object> paramMap);

	/**
	 *  修改任务线路表
	 * @param paramMap
	 * @return
	 */
	int updateSelectiveByMap(Map<String, Object> paramMap);

	/**  已改
	 * 根据任务单编号删除任务线路表
	 * @param taskNo
	 * @return
	 */
	int deleteByTaskNo(String taskNo);

	/**  已改
	 * 根据任务单编号查询线路编号和线路名称
	 * @param taskNo
	 * @return
	 */
	Map getLineByTaskNo(String taskNo);
}
