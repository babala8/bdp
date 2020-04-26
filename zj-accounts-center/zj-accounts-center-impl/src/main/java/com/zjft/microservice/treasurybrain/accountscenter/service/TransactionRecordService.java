package com.zjft.microservice.treasurybrain.accountscenter.service;

import com.zjft.microservice.treasurybrain.accountscenter.dto.BiztxlogDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/10/10
 */
public interface TransactionRecordService {

	PageDTO<BiztxlogDTO> qryByPage(Map<String, Object> paramMap);

	List<Map<String, Object>> qryDevDayAvgAmt(String devNo, String tranDate);

	List<Map<String, Object>> getMaxDateOfDev(String devNo);
}
