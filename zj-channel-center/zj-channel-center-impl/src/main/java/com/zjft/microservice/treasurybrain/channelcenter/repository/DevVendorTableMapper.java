package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.common.domain.DevVendorTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DevVendorTableMapper {

	/**
	 * 根据品牌名称模糊查询设备品牌列表
	 *
	 * @param name 品牌名称
	 * @return List<DevVendorTable>
	 */
	List<DevVendorTable> queryDevVendorListFuzzyByName(@Param("name") String name);

	int deleteByPrimaryKey(String no);

	/**
	 * 用于插入设备品牌信息时获取NO
	 *
	 * @return String
	 */
	String getMax1DevVendorNo();

	int insert(DevVendorTable record);

	int insertSelective(DevVendorTable record);

	DevVendorTable selectByPrimaryKey(String no);

	int updateByPrimaryKeySelective(DevVendorTable record);

	int updateByPrimaryKey(DevVendorTable record);
}