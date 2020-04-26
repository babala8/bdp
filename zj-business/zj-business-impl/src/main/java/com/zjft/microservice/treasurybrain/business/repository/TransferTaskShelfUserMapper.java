package com.zjft.microservice.treasurybrain.business.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TransferTaskShelfUserMapper {

	@Delete("delete from TASK_SHELF_USER where TASK_NO =#{taskNo,jdbcType=VARCHAR}")
	int deleteByPrimaryKey(String taskNo);
}
