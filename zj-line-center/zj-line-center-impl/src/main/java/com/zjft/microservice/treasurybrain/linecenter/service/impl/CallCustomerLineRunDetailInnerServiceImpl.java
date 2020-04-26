package com.zjft.microservice.treasurybrain.linecenter.service.impl;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineScheduleDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineScheduleDTO;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.LineCallCustomerLineRunDetailConverter;
import com.zjft.microservice.treasurybrain.linecenter.po.LineSchedulePO;
import com.zjft.microservice.treasurybrain.linecenter.repository.LineScheduleMapper;
import com.zjft.microservice.treasurybrain.linecenter.repository.LineWorkMapper;
import com.zjft.microservice.treasurybrain.linecenter.service.CallCustomerLineRunDetailInnerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 常 健
 * @since 2020/1/7
 */
@Service
public class CallCustomerLineRunDetailInnerServiceImpl implements CallCustomerLineRunDetailInnerService {

//	@Resource
//	private LineCallCustomerLineRunDetailMapper callCustomerLineRunDetailMapper;
//
//	@Resource
//	private LineCallCustomerLineRunMapper callCustomerLineRunMapper;

	@Resource
	private LineScheduleMapper lineScheduleMapper;

	@Resource
	private LineWorkMapper lineWorkMapper;

	@Override
	public int deleteByLineRunNo(String lineWorkId) {
//		return callCustomerLineRunDetailMapper.deleteByLineRunNo(lineRunNo);
		return lineScheduleMapper.deleteByLineWorkID(lineWorkId);
	}

	@Override
	public int insertByMap(Map<String, Object> map) {
//		return callCustomerLineRunDetailMapper.insertByMap(map);
		return lineScheduleMapper.insertByMap(map);
	}

	@Override
	public String qryLineRunNo(Map<String, Object> paramMap) {
//		return callCustomerLineRunMapper.selectLineRunNo(paramMap);
		return lineScheduleMapper.selectLineWorkID(paramMap);
	}

	@Override
	public LineScheduleDTO qryCallCustomerLineRunDetail(Map<String, Object> paramMap) {
//		LineSchedulePO lineSchedulePO = callCustomerLineRunDetailMapper.qryCallCustomerLineRunDetail(paramMap);
		LineScheduleDO lineScheduleDO = lineScheduleMapper.qryCallCustomerLineRunDetail(paramMap);
		return LineCallCustomerLineRunDetailConverter.INSTANCE.po2dto(lineScheduleDO);
	}

	@Override
	public int updateByPrimaryKeyMap(Map<String, Object> map) {
//		return callCustomerLineRunDetailMapper.updateByMap(map);
		return lineScheduleMapper.updateByMap(map);
	}

	@Override
	public int updateCustomerNumByNo(String lineRunNo) {
//		return callCustomerLineRunMapper.updateCustomerNumByNo(lineRunNo);
		return lineWorkMapper.updateCustomerNumByNo(lineRunNo);
	}
}
