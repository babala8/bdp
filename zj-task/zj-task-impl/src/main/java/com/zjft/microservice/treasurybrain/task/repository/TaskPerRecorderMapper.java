package com.zjft.microservice.treasurybrain.task.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TaskPerRecorderMapper {

	//插入任务执行记录表
	int insertByMap(Map<String,Object> paramMap);
}
