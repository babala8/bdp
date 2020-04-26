package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerTimeDO;
import com.zjft.microservice.treasurybrain.channelcenter.po.CallCustomerTimePO;
import com.zjft.microservice.treasurybrain.common.domain.CallCustomerTime;
import com.zjft.microservice.treasurybrain.common.domain.DevClrTimeParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CallCustomerTimeMapper {

	int deleteByCustomerNo(String customerNo);

	int insert(CallCustomerTime record);

	DevClrTimeParam selectByPrimaryKey(Map<String, Object> paramMap);

	int updateByPrimaryKey(CallCustomerTime record);

	List<String> selectlineNoList(String devNo);

	List selectDctVOList(Map<String, Object> paramMap);

	List<CallCustomerTimeDO> qryByCustomerNo(String customerNo);

	/**
	 *
	 *
	 * @param map lineNo 线路编号 weekday 星期几
	 * @return
	 */
	List<CallCustomerTimePO> qryByLineNoAndWeekDay(Map<String,Object> map);
}
