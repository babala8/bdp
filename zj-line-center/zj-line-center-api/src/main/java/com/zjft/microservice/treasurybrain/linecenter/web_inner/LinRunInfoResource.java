package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import java.util.List;
import java.util.Map;

public interface LinRunInfoResource {

	int insertSelectiveByMap(Map<String, Object> paramMap);

	int deleteLine(Map<String, Object> paramMap);

	List<String> selectTheYearMonthByLineNo(Map<String, Object> paramMap);
}
