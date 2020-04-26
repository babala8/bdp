package com.zjft.microservice.treasurybrain.business.web_inner;

import com.zjft.microservice.treasurybrain.business.dto.CurrencyTypeListDTO;
import com.zjft.microservice.treasurybrain.business.dto.TaskInOutDTO;
import com.zjft.microservice.treasurybrain.business.service.TaskInOutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class TaskInOutInnerResourceImpl implements TaskInOutInnerResource {
	@Resource
	private TaskInOutService taskInOutService;

	@Override
	public String selectSumAmount(Map<String, Object> paramMap) {
		return taskInOutService.selectSumAmount(paramMap);
	}

	@Override
	public List<CurrencyTypeListDTO> selectCurrencyTypeList(Map<String, Object> paramMap) {
		return taskInOutService.selectCurrencyTypeList(paramMap);
	}

	@Override
	public List<TaskInOutDTO> selectByTaskNo(String taskNo) {
		return taskInOutService.selectByTaskNo(taskNo);
	}

	@Override
	public int insertSelectiveByMap(Map<String, Object> taskInOutMap) {
		return taskInOutService.insertSelectiveByMap(taskInOutMap);
	}

	@Override
	public int deleteByTaskNo(String taskNo) {
		return taskInOutService.deleteByTaskNo(taskNo);
	}
}
