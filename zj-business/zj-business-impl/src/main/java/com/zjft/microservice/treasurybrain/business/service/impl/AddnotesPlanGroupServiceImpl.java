package com.zjft.microservice.treasurybrain.business.service.impl;

import com.zjft.microservice.treasurybrain.business.domain.AddnotesPlanGroup;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanGroupDTO;
import com.zjft.microservice.treasurybrain.business.mapstruct.AddnotesPlanGroupDTOConverter;
import com.zjft.microservice.treasurybrain.business.repository.AddnotesPlanGroupMapper;
import com.zjft.microservice.treasurybrain.business.service.AddnotesPlanGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AddnotesPlanGroupServiceImpl implements AddnotesPlanGroupService {
    @Resource
	private AddnotesPlanGroupMapper addnotesPlanGroupMapper;

	@Override
	public int insertSelectiveByMap(Map<String, Object> addnotesPlanGroupMap) {
		return addnotesPlanGroupMapper.insertSelectiveByMap(addnotesPlanGroupMap);
	}

	@Override
	public AddnotesPlanGroupDTO selectByPrimaryKeyMap(Map<String, Object> addnotesPlanGroupMap) {
		AddnotesPlanGroup addnotesPlanGroup = addnotesPlanGroupMapper.selectByPrimaryKeyMap(addnotesPlanGroupMap);
		AddnotesPlanGroupDTO addnotesPlanGroupDTO = AddnotesPlanGroupDTOConverter.INSTANCE.domain2dto(addnotesPlanGroup);
		return addnotesPlanGroupDTO;
	}

	@Override
	public int updateByPrimaryKeyMap(Map<String, Object> addnotesPlanGroupMap) {
		return addnotesPlanGroupMapper.updateByPrimaryKeyMap(addnotesPlanGroupMap);
	}

	@Override
	public void deleteByAddnotesPlanNo(String addnotesPlanNo) {
		addnotesPlanGroupMapper.deleteByAddnotesPlanNo(addnotesPlanNo);
	}

	@Override
	public List<String> getGroupNoListByNo(String addnotesPlanNo) {
		return addnotesPlanGroupMapper.getGroupNoListByNo(addnotesPlanNo);
	}
}
