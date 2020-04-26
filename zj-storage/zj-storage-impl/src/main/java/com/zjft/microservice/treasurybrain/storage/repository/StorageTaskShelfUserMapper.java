package com.zjft.microservice.treasurybrain.storage.repository;


import com.zjft.microservice.treasurybrain.storage.po.StorageTaskShelfUserPO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface StorageTaskShelfUserMapper {


	int insert(StorageTaskShelfUserPO record);

	int insertByMap(Map<String, Object> map);

	@Delete("delete from TASK_SHELF_USER where task_no =#{taskNo,jdbcType=VARCHAR}")
	int deleteByTaskNo(String taskNo);


}
