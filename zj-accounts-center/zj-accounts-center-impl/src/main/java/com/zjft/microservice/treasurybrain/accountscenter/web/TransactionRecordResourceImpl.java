package com.zjft.microservice.treasurybrain.accountscenter.web;

import com.zjft.microservice.treasurybrain.accountscenter.dto.BiztxlogDTO;
import com.zjft.microservice.treasurybrain.accountscenter.service.TransactionRecordService;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/10/10
 */
@RestController
@Slf4j
public class TransactionRecordResourceImpl implements TransactionRecordResource {

	@Resource
	private TransactionRecordService transactionRecordService;

	@Override
	public PageDTO<BiztxlogDTO> qryByPage(Map<String, Object> paramMap) {
		try {
			return transactionRecordService.qryByPage(paramMap);
		} catch (Exception e) {
			log.error("分页查询交易记录失败！", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}
}
