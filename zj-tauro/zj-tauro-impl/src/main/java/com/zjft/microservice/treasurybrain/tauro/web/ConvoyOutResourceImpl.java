package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.tauro.service.ConvoyOutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 葛瑞莲
 * @since 2019/8/26
 */
@Slf4j
@RestController
public class ConvoyOutResourceImpl implements ConvoyOutResource {

	@Resource
	private ConvoyOutService convoyOutService;

//	@Override
//	public DTO recover(TaskTransferDTO taskTransferDTO) {
//		try {
//			return convoyOutService.convoyOut(taskTransferDTO);
//		} catch (Exception e) {
//			log.error("入库异常", e);
//			return new DTO(RetCodeEnum.EXCEPTION.getCode(), e.getMessage());
//		}
//	}
}
