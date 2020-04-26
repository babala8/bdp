package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerLineRunDetailDTO;
import com.zjft.microservice.treasurybrain.channelcenter.service.CallCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-06
 */
@Slf4j
@RestController
public class CallCustomerInnerResourceImpl implements CallCustomerInnerResource{

	@Resource
	private CallCustomerService callCustomerService;

	@Override
	public String selectOutOrgLineNo(String customerNo){
		return callCustomerService.selectOutOrgLineNo(customerNo);
	}

	@Override
	public Map<String,Object> selectlineNo (String id){
		return callCustomerService.selectlineNo(id);
	}

	@Override
	public CallCustomerDTO selectByPrimaryKey(String customerNo) {

		return callCustomerService.selectByPrimaryKey(customerNo);

	}

	@Override
	public void updateByPrimaryKeySelective(CallCustomerDTO callCustomerDTO) {

		callCustomerService.updateByPrimaryKeySelective(callCustomerDTO);

	}


}
