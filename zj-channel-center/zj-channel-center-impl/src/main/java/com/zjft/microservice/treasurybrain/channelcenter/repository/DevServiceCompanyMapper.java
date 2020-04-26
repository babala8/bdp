package com.zjft.microservice.treasurybrain.channelcenter.repository;


import com.zjft.microservice.treasurybrain.common.domain.DevServiceCompany;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("devServiceCompanyMapperExtendBase")
public interface DevServiceCompanyMapper {
	int deleteByPrimaryKey(String no);

	int insert(DevServiceCompany record);

	int insertSelective(DevServiceCompany record);

	DevServiceCompany selectByPrimaryKey(String no);

	int updateByPrimaryKeySelective(DevServiceCompany record);

	int updateByPrimaryKey(DevServiceCompany record);

	String selectMaxNo();

	List<DevServiceCompany> queryDevCompanyListByName(@Param("name") String name,@Param("type") String type);

	String qryNoByName(String name);
}
