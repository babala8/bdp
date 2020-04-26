package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineRunDevDetailDO;
import com.zjft.microservice.treasurybrain.linecenter.domain.LineRunDevDetailExpandDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LineRunDevDetailMapper {
    int deleteByPrimaryKey(LineRunDevDetailDO key);

	int deleteByLineRunNo(String lineRunNo);

    int insert(LineRunDevDetailExpandDO record);

    int insertSelective(LineRunDevDetailExpandDO record);

	int insertSelectiveByMap(Map<String,Object> lineRunDevDetailMap);

    LineRunDevDetailExpandDO selectByPrimaryKey(LineRunDevDetailDO key);

    int updateByPrimaryKeySelective(LineRunDevDetailExpandDO record);

    int updateByPrimaryKey(LineRunDevDetailExpandDO record);

	int deleteLineDetail(Map<String, Object> paramMap);
}
