package com.zjft.microservice.treasurybrain.param.repository;

import com.zjft.microservice.treasurybrain.param.domain.DevRuleExec;
import com.zjft.microservice.treasurybrain.param.domain.DevRuleExecKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DevRuleExecMapper {

    int deleteByPrimaryKey(DevRuleExecKey key);

    int insert(DevRuleExec record);

    int insertSelective(DevRuleExec record);

    DevRuleExec selectByPrimaryKey(DevRuleExecKey key);

    int updateByPrimaryKeySelective(DevRuleExec record);

    int updateByPrimaryKey(DevRuleExec record);

	DevRuleExec selectDevRuleExecByKey(DevRuleExecKey devRuleExecKey);
	
	int qryTotalRowRule(Map<String, Object> paramMap);

	List<DevRuleExec> qryDevRuleExec(Map<String, Object> paramMap);

	List<DevRuleExec> isConflict(DevRuleExec devRuleExec);

}
