package com.zjft.microservice.treasurybrain.channelcenter.service;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerInfo;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerLineRunDetailDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTypeDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author zhangjs
 * @since 2019-09-21
 */
public interface CallCustomerService {

	PageDTO<CallCustomerDTO> queryCallCustomerList(Map<String, Object> page);


	DTO addCallCustomer(CallCustomerDTO dto);


	DTO modCallCustomer(CallCustomerDTO dto);


	DTO delCallCustomerByNo(String no);

	CallCustomerDTO selectByPrimaryKey(String customerNo);

	void updateByPrimaryKeySelective(CallCustomerDTO callCustomerDTO);

	String selectOutOrgLineNo(String customerNo);

	Map<String,Object> selectlineNo (String id);

	PageDTO<CallCustomerTypeDTO> queryCallCustomerTypeListByPage(Map<String,Object> map);

	ListDTO<CallCustomerTypeDTO> queryCallCustomerTypeList(Map<String,Object> map);

}
