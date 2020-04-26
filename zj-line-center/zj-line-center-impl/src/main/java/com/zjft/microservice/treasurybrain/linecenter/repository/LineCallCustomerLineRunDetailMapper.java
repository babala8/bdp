package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.po.LineSchedulePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/24 00:18
 */
@Mapper
public interface LineCallCustomerLineRunDetailMapper {

	int deleteByLineRunNo(String lineRunNo);

	int insert(LineSchedulePO lineCallCustomerLineRunDetailPO);

	int insertByMap(Map<String,Object> map);

	LineSchedulePO qryCallCustomerLineRunDetail(Map<String, Object> paramMap);

	int updateByMap(Map<String,Object> map);
}
