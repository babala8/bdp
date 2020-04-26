package com.zjft.microservice.treasurybrain.param.repository;

import com.zjft.microservice.treasurybrain.param.domain.AddnotesRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddnotesRuleMapper {

    int deleteByPrimaryKey(String ruleId);

    int insert(AddnotesRule record);

    int insertSelective(AddnotesRule record);

    AddnotesRule selectByPrimaryKey(String ruleId);

    int updateByPrimaryKeySelective(AddnotesRule record);

    int updateByPrimaryKey(AddnotesRule record);

	int qryTotalRowRule(Map<String, Object> paramMap);

	List<AddnotesRule> qryAddnotesRule(Map<String, Object> paramMap);

	String selectMaxRuleId(@Param("clrCenterNo") String clrCenterNo, @Param("ruleGenDate") String ruleGenDate);

	AddnotesRule selectDetailByPrimaryKey(String ruleId);

	String qryRuleDesp(String ruleId);

	List<String> qryNearby(Map<String, Object> paramMap);
    
}
