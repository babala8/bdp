package com.zjft.microservice.treasurybrain.linecenter.service.impl;

import com.zjft.microservice.treasurybrain.linecenter.repository.LineWorkMapper;
import com.zjft.microservice.treasurybrain.linecenter.service.LineWorkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/2/27
 */
@Service
public class LineWorkServiceImpl implements LineWorkService {

	@Resource
	private LineWorkMapper lineWorkMapper;

	@Override
	public List<String> qryTaskNoByLineNo(String lineNo) {
		List<String> lineWorkIdList = lineWorkMapper.qryLineWorkIdByLineNo(lineNo);
		List<String> taskNoList = lineWorkMapper.qryTaskNoByLineNo(lineWorkIdList);
		return taskNoList;
	}

	@Override
	public String qryLineWorkId(Map<String, Object> paramMap) {
		return lineWorkMapper.qryLineWorkId(paramMap);
	}
}
