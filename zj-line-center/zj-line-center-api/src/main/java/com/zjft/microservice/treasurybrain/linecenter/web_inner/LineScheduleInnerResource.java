package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;

import java.util.Map;

public interface LineScheduleInnerResource {
	int insertSelectiveByMap(Map<String,Object> lineRunDevDetailMap);

	int deleteLineDetail(Map<String, Object> paramMap);

	LineScheduleDTO selectByMap(Map<String, Object> paramMap);

}
