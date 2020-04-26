package com.zjft.microservice.treasurybrain.accountscenter.service.impl;

import com.zjft.microservice.treasurybrain.accountscenter.domain.BiztxlogDO;
import com.zjft.microservice.treasurybrain.accountscenter.dto.BiztxlogDTO;
import com.zjft.microservice.treasurybrain.accountscenter.mapstruct.TransactionRecordConverter;
import com.zjft.microservice.treasurybrain.accountscenter.repository.BiztxlogMapper;
import com.zjft.microservice.treasurybrain.accountscenter.service.TransactionRecordService;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/10/10
 */
@Service
@Slf4j
public class TransactionRecordServiceImpl implements TransactionRecordService {

	@Resource
	private BiztxlogMapper biztxlogMapper;

	@Override
	public PageDTO<BiztxlogDTO> qryByPage(Map<String, Object> paramMap) {
		PageDTO<BiztxlogDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		Integer pageSize = PageUtil.transParam2Page(paramMap, pageDTO);

		int totalRow = biztxlogMapper.qryTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<BiztxlogDO> doList = biztxlogMapper.queryInfoByPage(paramMap);
		List<BiztxlogDTO> dtoList = TransactionRecordConverter.INSTANCE.domain2dto(doList);

		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setPageSize(dtoList.size());
		pageDTO.setRetList(dtoList);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public List<Map<String, Object>> qryDevDayAvgAmt(String devNo, String tranDate){
		return biztxlogMapper.qryDevDayAvgAmt(devNo,tranDate);
	}

	@Override
	public List<Map<String, Object>> getMaxDateOfDev(String devNo){
		return biztxlogMapper.getMaxDateOfDev(devNo);
	}

}
