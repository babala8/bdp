package com.zjft.microservice.treasurybrain.timejob.repository;

import com.zjft.microservice.treasurybrain.timejob.po.CityInfoPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityInfoMapper {

	CityInfoPO getCityInfo(String cityNo);

	List<CityInfoPO> getCityInfoList();

}
