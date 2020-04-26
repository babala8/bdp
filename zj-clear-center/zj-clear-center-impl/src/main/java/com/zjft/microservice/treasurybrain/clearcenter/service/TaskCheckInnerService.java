package com.zjft.microservice.treasurybrain.clearcenter.service;

import com.zjft.microservice.treasurybrain.clearcenter.dto.TaskCheckDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/2
 */
public interface TaskCheckInnerService {

	List<TaskCheckDTO> qryInfoByTaskNo(String taskNo);

	BigDecimal qryCheckAmount(String taskNo);
}
