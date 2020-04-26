package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.service.DevLineRunService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class LinRunInfoResourceImpl implements LinRunInfoResource {
	@Resource
	private DevLineRunService devLineRunService;

	@Override
	public int insertSelectiveByMap(Map<String, Object> paramMap) {
		return devLineRunService.insertSelectiveByMap(paramMap);
	}

	@Override
	public int deleteLine(Map<String, Object> paramMap) {
		return devLineRunService.deleteLine(paramMap);
	}

	@Override
	public List<String> selectTheYearMonthByLineNo(Map<String, Object> paramMap) {
		return devLineRunService.selectTheYearMonthByLineNo(paramMap);
	}
}
