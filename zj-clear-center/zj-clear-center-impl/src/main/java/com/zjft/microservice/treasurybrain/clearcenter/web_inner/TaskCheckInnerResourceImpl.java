package com.zjft.microservice.treasurybrain.clearcenter.web_inner;

import com.zjft.microservice.treasurybrain.clearcenter.dto.TaskCheckDTO;
import com.zjft.microservice.treasurybrain.clearcenter.service.TaskCheckInnerService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/2
 */
@RestController
public class TaskCheckInnerResourceImpl implements TaskCheckInnerResource{

	@Resource
	private TaskCheckInnerService taskCheckInnerService;

	@Override
	public List<TaskCheckDTO> qryInfoByTaskNo(String taskNo) {
		return taskCheckInnerService.qryInfoByTaskNo(taskNo);
	}

	@Override
	public BigDecimal qryCheckAmount(String taskNo) {
		return taskCheckInnerService.qryCheckAmount(taskNo);
	}
}
