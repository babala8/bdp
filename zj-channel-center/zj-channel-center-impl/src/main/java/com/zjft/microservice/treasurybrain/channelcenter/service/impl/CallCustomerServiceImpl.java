package com.zjft.microservice.treasurybrain.channelcenter.service.impl;

import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerInfo;
import com.zjft.microservice.treasurybrain.channelcenter.domain.CallCustomerTypeDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTypeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.CallCustomerInfoConverter;
import com.zjft.microservice.treasurybrain.channelcenter.repository.CallCustomerTableMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.CallCustomerService;
import com.zjft.microservice.treasurybrain.common.dto.ClrCenterDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 *
 * @author zhangjs
 * @since 2019-09-21
 */
@Slf4j
@Service
public class CallCustomerServiceImpl implements CallCustomerService {

	@Resource
	private CallCustomerTableMapper callCustomerTableMapper;

	@Override
	public PageDTO<CallCustomerDTO> queryCallCustomerList(Map<String, Object> params) {
		PageDTO<CallCustomerDTO> dto = new PageDTO<>(RetCodeEnum.FAIL);

		int curPage = StringUtil.objectToInt(params.get("curPage"));
		int pageSize = StringUtil.objectToInt(params.get("pageSize"));
		int startRow = pageSize * (curPage - 1);
		int endRow = pageSize * curPage;
		params.put("startRow", startRow);
		params.put("endRow", endRow);

		int totalRow = callCustomerTableMapper.qryTotalRow(params);
		int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

		List<CallCustomerInfo> callCustomerInfoList = callCustomerTableMapper.selectCallCustomerListByParams(params);
		List<CallCustomerDTO> dtoList = CallCustomerInfoConverter.INSTANCE.domain2dto(callCustomerInfoList);

		List<CallCustomerDTO> retList = new ArrayList<CallCustomerDTO>();
		for (CallCustomerInfo callCustomerInfo : callCustomerInfoList) {
			CallCustomerDTO callCustomerDTO = CallCustomerInfoConverter.INSTANCE.domain2dto(callCustomerInfo);
			callCustomerDTO.setCallCustomerLineName(callCustomerInfo.getCallCustomerLineName());
			retList.add(callCustomerDTO);
		}

		dto.setPageSize(pageSize);
		dto.setRetList(retList);
		dto.setTotalRow(totalRow);
		dto.setTotalPage(totalPage);
		dto.setCurPage(curPage);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	@Override
	public DTO addCallCustomer(CallCustomerDTO callCustomerDTO) {
		log.info("----------[CallCustomerService]addCallCustomer----------------");
		CallCustomerInfo callCustomerInfo = CallCustomerInfoConverter.INSTANCE.dto2domain(callCustomerDTO);
		callCustomerInfo.setCustomerNo(UUID.randomUUID().toString().replace("-", ""));
		int i = callCustomerTableMapper.insert(callCustomerInfo);
		if (i > 0) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO modCallCustomer(CallCustomerDTO callCustomerDTO) {
		log.info("----------[CallCustomerService]modCallCustomer----------------");
		CallCustomerInfo callCustomerInfo = CallCustomerInfoConverter.INSTANCE.dto2domain(callCustomerDTO);
		int i = callCustomerTableMapper.updateByPrimaryKeySelective(callCustomerInfo);
		if (i > 0) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO delCallCustomerByNo(String no) {
		log.info("----------[CallCustomerService]delCallCustomerByNo----------------");
		int i = callCustomerTableMapper.deleteByPrimaryKey(no);
		if (i > 0) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public CallCustomerDTO selectByPrimaryKey(String customerNo) {
		return CallCustomerInfoConverter.INSTANCE.domain2dto(callCustomerTableMapper.selectByPrimaryKey(customerNo));
	}

	@Override
	public void updateByPrimaryKeySelective(CallCustomerDTO callCustomerDTO) {
		callCustomerTableMapper.updateByPrimaryKeySelective(CallCustomerInfoConverter.INSTANCE.dto2domain(callCustomerDTO));
	}

	@Override
	public String selectOutOrgLineNo(String customerNo){
		return callCustomerTableMapper.selectOutOrgLineNo(customerNo);
	}

	@Override
	public Map<String,Object> selectlineNo (String id){
		return callCustomerTableMapper.selectlineNo(id);
	}

	@Override
	public PageDTO<CallCustomerTypeDTO> queryCallCustomerTypeListByPage(Map<String, Object> map) {
		PageDTO<CallCustomerTypeDTO> dto = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(map, dto);
		int totalRow = callCustomerTableMapper.qryTypeTotalRow(map);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);
		List<CallCustomerTypeDO> callCustomerTypeList = callCustomerTableMapper.queryCallCustomerTypeListByPage(map);
		List<CallCustomerTypeDTO> callCustomerTypeDTOList = CallCustomerInfoConverter.INSTANCE.domain2dto2(callCustomerTypeList);

		dto.setPageSize(pageSize);
		dto.setRetList(callCustomerTypeDTOList);
		dto.setTotalRow(totalRow);
		dto.setTotalPage(totalPage);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	@Override
	public ListDTO<CallCustomerTypeDTO> queryCallCustomerTypeList(Map<String, Object> map) {
		ListDTO<CallCustomerTypeDTO> listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		try{
		List<CallCustomerTypeDO> callCustomerTypeList = callCustomerTableMapper.queryCallCustomerTypeList(map);
		List<CallCustomerTypeDTO> callCustomerTypeDTOList = CallCustomerInfoConverter.INSTANCE.domain2dto2(callCustomerTypeList);
		listDTO.setRetList(callCustomerTypeDTOList);
		listDTO.setResult(RetCodeEnum.SUCCEED);
		}catch (Exception e){
			listDTO.setResult(RetCodeEnum.FAIL);
		}
		return listDTO;
	}
}
