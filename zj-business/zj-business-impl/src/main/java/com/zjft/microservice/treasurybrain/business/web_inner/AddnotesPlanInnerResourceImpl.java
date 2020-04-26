package com.zjft.microservice.treasurybrain.business.web_inner;

import com.zjft.microservice.treasurybrain.business.dto.AddnotesLineDTO;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDTO;
import com.zjft.microservice.treasurybrain.business.service.AddnotesLineService;
import com.zjft.microservice.treasurybrain.business.service.AddnotesPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class AddnotesPlanInnerResourceImpl implements AddnotesPlanInnerResource {
	@Resource
	private AddnotesLineService addnotesLineService;

	@Resource
	AddnotesPlanService addnotesPlanService;

	@Override
	public AddnotesPlanDTO selectByPrimaryKey(String addnotesPlanNo) {
		return addnotesPlanService.selectByPrimaryKey(addnotesPlanNo);
	}

	@Override
	public int updateByPrimaryKeyByMap(Map<String, Object> addnotesPlanMap) {
		return addnotesPlanService.updateByPrimaryKeyByMap(addnotesPlanMap);
	}

	@Override
	public AddnotesPlanDTO selectDetailByPrimaryKey(String addnotesPlanNo) {
		return addnotesPlanService.selectDetailByPrimaryKey(addnotesPlanNo);
	}

}
