package com.zjft.microservice.treasurybrain.clearcenter.repository;

import com.zjft.microservice.treasurybrain.clearcenter.domain.TaskCountInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CountTaskInfoMapper {

	int deleteByPrimaryKey(String taskNo);

    int insert(TaskCountInfoDO record);

    int insertSelective(TaskCountInfoDO record);

    int updateByPrimaryKeySelective(TaskCountInfoDO record);

    int updateByPrimaryKey(TaskCountInfoDO record);

    int updateNextAdd(TaskCountInfoDO record);

    int updateNextDel(TaskCountInfoDO record);

    List<TaskCountInfoDO> selectByBatch(TaskCountInfoDO record);

    TaskCountInfoDO selectByPrimaryKey(String taskNo);

	List<TaskCountInfoDO> qryCountTaskListByPage(Map<String, Object> paramMap);

	int qryTotalRow(Map<String, Object> paramMap);

	List<TaskCountInfoDO> selectByDevNo(String devNo);

}
