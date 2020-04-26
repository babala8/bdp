package com.zjft.microservice.treasurybrain.param.repository;

import com.zjft.microservice.treasurybrain.param.domain.SpdateCoeff;
import com.zjft.microservice.treasurybrain.param.domain.SpdateCoeffKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface SpdateCoeffMapper {

    int deleteByPrimaryKey(SpdateCoeffKey key);

    int insert(SpdateCoeff record);

    int insertSelective(SpdateCoeff record);

    SpdateCoeff selectByPrimaryKey(SpdateCoeffKey key);

    int updateByPrimaryKeySelective(SpdateCoeff record);

    int updateByPrimaryKey(SpdateCoeff record);

	SpdateCoeff selectSpdateCoeffByKey(SpdateCoeffKey spdateCoeffKey);
	
	int qryTotalRowRule(Map<String, Object> paramMap);

	List<Map<String, Object>> qrySpdateCoeff(Map<String, Object> paramMap);

	SpdateCoeff isConflict(SpdateCoeff spdateCoeff);
	
}
