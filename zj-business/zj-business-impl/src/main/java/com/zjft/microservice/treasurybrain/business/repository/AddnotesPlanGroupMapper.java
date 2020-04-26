package com.zjft.microservice.treasurybrain.business.repository;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlanGroup;
import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlanGroupKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddnotesPlanGroupMapper {
    int deleteByPrimaryKey(AddnotesPlanGroupKey key);

    int insert(AddnotesPlanGroup record);

    int insertSelective(AddnotesPlanGroup record);

    AddnotesPlanGroup selectByPrimaryKey(AddnotesPlanGroupKey key);

    int updateByPrimaryKeySelective(AddnotesPlanGroup record);

    int updateByPrimaryKey(AddnotesPlanGroup record);

	void deleteByAddnotesPlanNo(String addnotesPlanNo);
	
	List<AddnotesPlanGroup> getGroupsByNo(String addnotesPlanNo);
	
	List<String> getGroupNoListByNo(String addnotesPlanNo);

	int insertSelectiveByMap( Map<String, Object> addnotesPlanGroupMap);

	AddnotesPlanGroup selectByPrimaryKeyMap(Map<String, Object> addnotesPlanGroupMap);

	int updateByPrimaryKeyMap(Map<String, Object> addnotesPlanGroupMap);
}
