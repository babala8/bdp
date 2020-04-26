package com.zjft.microservice.treasurybrain.channelcenter.repository;


import com.zjft.microservice.treasurybrain.common.domain.DevCatalogTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DevCatalogTableMapper {

	int deleteByPrimaryKey(Integer no);

	int insert(DevCatalogTable record);

	int insertSelective(DevCatalogTable record);

	DevCatalogTable selectByPrimaryKey(Integer no);

	int updateByPrimaryKeySelective(DevCatalogTable record);

	int updateByPrimaryKey(DevCatalogTable record);

	List<DevCatalogTable> queryDevCatalogList();
}