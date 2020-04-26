package com.zjft.microservice.treasurybrain.linecenter.repository;


import com.zjft.microservice.treasurybrain.linecenter.domain.LineScheduleDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.po.LineSchedulePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LineScheduleMapper {
    int insert(LineScheduleDO record);

    int insertSelective(LineScheduleDO record);

	int deleteByLineWorkID(String lineWorkId);

	int insertByMap(Map<String,Object> map);

	String  selectLineWorkID(Map<String, Object> paramMap);

	LineScheduleDO qryCallCustomerLineRunDetail(Map<String, Object> paramMap);

	int updateByMap(Map<String,Object> map);

	int insertSelectiveByMap(Map<String,Object> lineRunDevDetailMap);

	int deleteLineDetail(Map<String, Object> paramMap);

	LineScheduleDO selectDetailByPrimaryKey(LineScheduleDO lineScheduleDO);

	int deleteNetworkLineDetail(Map<String, Object> paramMap);

	LineScheduleDO selectByMap(Map<String, Object> paramMap);
}
