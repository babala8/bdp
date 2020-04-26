package com.zjft.microservice.treasurybrain.business.service.impl;

import com.zjft.microservice.treasurybrain.business.dto.CurrencyTypeListDTO;
import com.zjft.microservice.treasurybrain.business.dto.TaskInOutDTO;
import com.zjft.microservice.treasurybrain.business.mapstruct.TaskInOutDTOConverter;
import com.zjft.microservice.treasurybrain.business.po.TransferTaskInOutPo;
import com.zjft.microservice.treasurybrain.business.repository.TaskInOutMapper;
import com.zjft.microservice.treasurybrain.business.service.TaskInOutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class TaskInOutServiceImpl implements TaskInOutService {
	@Resource
	private TaskInOutMapper taskInOutMapper;

	@Override
	public String selectSumAmount(Map<String, Object> paramMap) {
		return taskInOutMapper.selectSumAmount(paramMap);
	}

	@Override
	public List<CurrencyTypeListDTO> selectCurrencyTypeList(Map<String, Object> paramMap) {
		return taskInOutMapper.selectCurrencyTypeList(paramMap);
	}

	@Override
	public List<TaskInOutDTO> selectByTaskNo(String taskNo) {
		List<TransferTaskInOutPo> taskInOutList = taskInOutMapper.selectByTaskNo(taskNo);
		List<TaskInOutDTO> taskInOutDTOList = TaskInOutDTOConverter.INSTANCE.domain2dto2(taskInOutList);
		return taskInOutDTOList;
	}

	@Override
	public int insertSelectiveByMap(Map<String, Object> taskInOutMap) {
		return taskInOutMapper.insertSelectiveByMap(taskInOutMap);
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskInOutMapper.deleteByTaskNo(taskNo);
	}
}
