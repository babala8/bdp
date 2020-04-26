package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.common.domain.DevTypeTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("devTypeTableMapperExtendBase")
public interface DevTypeTableMapper {

	List<DevTypeTable> queryDevTypeList(@Param("name") String name);

	int deleteByPrimaryKey(String no);

	/**
	 * 插入设备型号信息时获取No
	 *
	 * @return String
	 */
	String getMax1DevTypeNo();

	int insert(DevTypeTable record);

	int insertSelective(DevTypeTable record);

	DevTypeTable selectByPrimaryKey(String no);

	int updateByPrimaryKeySelective(DevTypeTable record);

	int updateByPrimaryKey(DevTypeTable record);

	DevTypeTable selectDetailByPrimaryKey(String no);
}