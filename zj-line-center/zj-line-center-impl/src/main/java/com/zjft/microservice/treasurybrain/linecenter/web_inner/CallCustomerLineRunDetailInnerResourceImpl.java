package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.dto.CallCustomerLineRunDetailDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.CallCustomerLineRunDetailInnerService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/1/7
 */
@RestController
public class CallCustomerLineRunDetailInnerResourceImpl implements CallCustomerLineRunDetailInnerResource {

	@Resource
	private CallCustomerLineRunDetailInnerService callCustomerLineRunDetailInnerService;

	@Override
	public int deleteByLineRunNo(String lineRunNo) {
		return callCustomerLineRunDetailInnerService.deleteByLineRunNo(lineRunNo);
	}

	@Override
	public int insertByMap(Map<String, Object> map) {
		return callCustomerLineRunDetailInnerService.insertByMap(map);
	}

	@Override
	public String qryLineRunNo(Map<String, Object> paramMap) {
		return callCustomerLineRunDetailInnerService.qryLineRunNo(paramMap);
	}

	@Override
	public LineScheduleDTO qryCallCustomerLineRunDetail(Map<String, Object> paramMap) {
		return callCustomerLineRunDetailInnerService.qryCallCustomerLineRunDetail(paramMap);
	}

	@Override
	public int updateByPrimaryKeyMap(Map<String, Object> map) {
		return callCustomerLineRunDetailInnerService.updateByPrimaryKeyMap(map);
	}

	@Override
	public int updateCustomerNumByNo(String lineRunNo) {
		return callCustomerLineRunDetailInnerService.updateCustomerNumByNo(lineRunNo);
	}
}
