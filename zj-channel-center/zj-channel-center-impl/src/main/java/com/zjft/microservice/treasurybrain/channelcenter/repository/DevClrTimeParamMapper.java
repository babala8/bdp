package com.zjft.microservice.treasurybrain.channelcenter.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DevClrTimeParamMapper {

	int deleteByDevNo(String devNo);

	int insert(Map<String,Object> record);

	List<String> selectlineNoList(String devNo);

	List<Map<String, Object>> selectDctVOList(Map<String, Object> paramMap);

}
