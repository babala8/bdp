package com.zjft.microservice.treasurybrain.channelcenter.repository;


import com.zjft.microservice.treasurybrain.common.domain.DevStatusTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("devStatusTableMapperExtendBase")
public interface DevStatusTableMapper {
	int deleteByPrimaryKey(String devNo);

	int insert(DevStatusTable record);

	int insertSelective(DevStatusTable record);

	DevStatusTable selectByPrimaryKey(String devNo);

	int updateByPrimaryKeySelective(DevStatusTable record);

	int updateByPrimaryKey(DevStatusTable record);

	List<Map<String, Object>> queryForList(String devNosSb);
}