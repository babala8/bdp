package com.zjft.microservice.treasurybrain.task.repository;

import com.zjft.microservice.treasurybrain.task.domain.TaskDetailPropertyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskDetailPropertyMapper {

	int deleteByPrimaryKey(String id);

	int deleteByTaskNo(String taskNo);

	int insert(TaskDetailPropertyDO record);

	int insertSelective(TaskDetailPropertyDO record);

	int insertSelectiveByMap(Map<String,Object> map);

	TaskDetailPropertyDO selectByPrimaryKey(String id);

	List<TaskDetailPropertyDO> selectByDetailId(String detailId);

	int updateByPrimaryKeySelective(TaskDetailPropertyDO record);

	int updateByPrimaryKey(TaskDetailPropertyDO record);

}
