package com.zjft.microservice.treasurybrain.business.service;

import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanGroupDTO;

import java.util.List;
import java.util.Map;

public interface AddnotesPlanGroupService {

	int insertSelectiveByMap( Map<String, Object> addnotesPlanGroupMap);

	AddnotesPlanGroupDTO selectByPrimaryKeyMap(Map<String, Object> addnotesPlanGroupMap);

	int updateByPrimaryKeyMap(Map<String, Object> addnotesPlanGroupMap);

	void deleteByAddnotesPlanNo(String addnotesPlanNo);

	List<String> getGroupNoListByNo(String addnotesPlanNo);
}
