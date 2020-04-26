package com.zjft.microservice.treasurybrain.channelcenter.service;

import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTimeListDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/17 10:32
 */

public interface CallCustomerTimeService {

	/**
	 *
	 * 查询单个上门客户的周期信息
	 *
	 * @param customerNo 上门客户
	 * @return
	 */
	CallCustomerTimeListDTO qryByCustomerNo(String customerNo);

	DTO modCallCustomerTime(CallCustomerTimeListDTO dto) throws ParseException;

	List<CallCustomerTimeDTO> qryByLineAndWeekDay(Map<String,Object> map);

}
