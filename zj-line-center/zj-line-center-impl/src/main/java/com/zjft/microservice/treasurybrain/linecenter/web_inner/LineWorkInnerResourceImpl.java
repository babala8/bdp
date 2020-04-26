package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.service.LineWorkService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/2/27
 */
@RestController
public class LineWorkInnerResourceImpl implements LineWorkInnerResource{

	@Resource
	private LineWorkService lineWorkService;


	@Override
	public List<String> qryTaskNoByLineNo(String lineNo) {
		return lineWorkService.qryTaskNoByLineNo(lineNo);
	}

	@Override
	public String qryLineWorkId(Map<String, Object> paramMap) {
		return lineWorkService.qryLineWorkId(paramMap);
	}
}
