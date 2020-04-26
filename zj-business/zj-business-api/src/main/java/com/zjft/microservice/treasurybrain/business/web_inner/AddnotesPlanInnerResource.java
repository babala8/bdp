package com.zjft.microservice.treasurybrain.business.web_inner;

import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDTO;

import java.util.Map;

public interface AddnotesPlanInnerResource {

	AddnotesPlanDTO selectByPrimaryKey(String addnotesPlanNo);

	int updateByPrimaryKeyByMap(Map<String, Object> addnotesPlanMap);

	AddnotesPlanDTO selectDetailByPrimaryKey(String addnotesPlanNo);
}
