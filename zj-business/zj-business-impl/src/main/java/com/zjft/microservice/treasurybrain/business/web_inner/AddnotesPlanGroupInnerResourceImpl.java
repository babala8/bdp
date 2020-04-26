package com.zjft.microservice.treasurybrain.business.web_inner;

import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanGroupDTO;
import com.zjft.microservice.treasurybrain.business.service.AddnotesPlanGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class AddnotesPlanGroupInnerResourceImpl implements AddnotesPlanGroupInnerResource {
	@Resource
	private AddnotesPlanGroupService addnotesPlanGroupService;

	@Override
	public int insertSelectiveByMap( Map<String, Object> addnotesPlanGroupMap) {
		return addnotesPlanGroupService.insertSelectiveByMap(addnotesPlanGroupMap);
	}

	@Override
	public AddnotesPlanGroupDTO selectByPrimaryKeyMap(Map<String, Object> addnotesPlanGroupMap) {
		return addnotesPlanGroupService.selectByPrimaryKeyMap(addnotesPlanGroupMap);
	}

	@Override
	public int updateByPrimaryKeyMap(Map<String, Object> addnotesPlanGroupMap) {
		return addnotesPlanGroupService.updateByPrimaryKeyMap(addnotesPlanGroupMap);
	}

	@Override
	public void deleteByAddnotesPlanNo(String addnotesPlanNo) {
		 addnotesPlanGroupService.deleteByAddnotesPlanNo(addnotesPlanNo);
	}

	@Override
	public List<String> getGroupNoListByNo(String addnotesPlanNo) {
		return addnotesPlanGroupService.getGroupNoListByNo(addnotesPlanNo);
	}
}
