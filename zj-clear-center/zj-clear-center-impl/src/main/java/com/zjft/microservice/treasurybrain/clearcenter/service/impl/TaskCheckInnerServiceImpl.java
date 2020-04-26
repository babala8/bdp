package com.zjft.microservice.treasurybrain.clearcenter.service.impl;

import com.zjft.microservice.treasurybrain.clearcenter.dto.TaskCheckDTO;
import com.zjft.microservice.treasurybrain.clearcenter.mapstruct.TaskCheckInnerConverter;
import com.zjft.microservice.treasurybrain.clearcenter.po.TaskCheckPO;
import com.zjft.microservice.treasurybrain.clearcenter.repository.BanknoteTaskCheckMapper;
import com.zjft.microservice.treasurybrain.clearcenter.service.TaskCheckInnerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/2
 */
@Service
public class TaskCheckInnerServiceImpl implements TaskCheckInnerService {

	@Resource
	private BanknoteTaskCheckMapper banknoteTaskCheckMapper;

	@Override
	public List<TaskCheckDTO> qryInfoByTaskNo(String taskNo) {
		List<TaskCheckPO> poList = banknoteTaskCheckMapper.selectByTaskNo(taskNo);
		List<TaskCheckDTO> dtoList = TaskCheckInnerConverter.INSTANCE.po2dto(poList);
		return dtoList;
	}

	@Override
	public BigDecimal qryCheckAmount(String taskNo) {
		return banknoteTaskCheckMapper.selectCheckAmount(taskNo);
	}
}
