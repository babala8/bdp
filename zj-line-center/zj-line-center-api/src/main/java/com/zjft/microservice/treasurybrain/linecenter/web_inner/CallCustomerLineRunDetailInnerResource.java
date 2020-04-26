package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/1/7
 */
public interface CallCustomerLineRunDetailInnerResource {

	int deleteByLineRunNo(String lineRunNo);

	int insertByMap(Map<String, Object> map);

	String qryLineRunNo(Map<String, Object> paramMap);

	LineScheduleDTO qryCallCustomerLineRunDetail(Map<String, Object> paramMap);

	int updateByPrimaryKeyMap(Map<String, Object> map);

	int updateCustomerNumByNo(String lineRunNo);
}
