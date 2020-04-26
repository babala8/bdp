package com.zjft.microservice.treasurybrain.accountscenter.web_inner;

import com.zjft.microservice.treasurybrain.accountscenter.service.TransactionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-07
 */
@Slf4j
@RestController
public class BiztxlogInitInnerResourceImpl implements BiztxlogInitInnerResource{

	@Resource
	private TransactionRecordService transactionRecordService;

	@Override
	public List<Map<String, Object>> qryDevDayAvgAmt(String devNo, String tranDate){
		return transactionRecordService.qryDevDayAvgAmt(devNo,tranDate);
	}

	@Override
	public List<Map<String, Object>> getMaxDateOfDev(String devNo){
		return transactionRecordService.getMaxDateOfDev(devNo);
	}

}
