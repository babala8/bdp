package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.DevLineRunService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class LineScheduleInnerResourceImpl implements LineScheduleInnerResource {

	@Resource
	private DevLineRunService devLineRunService;

	@Override
	public int insertSelectiveByMap(Map<String, Object> lineRunDevDetailMap) {
		return devLineRunService.insertDetailSelectiveByMap(lineRunDevDetailMap);
	}

	@Override
	public int deleteLineDetail(Map<String, Object> paramMap) {
		return devLineRunService.deleteLineDetail(paramMap);
	}

	@Override
	public LineScheduleDTO selectByMap(Map<String, Object> paramMap) {
		return devLineRunService.selectByMap(paramMap);
	}
}
