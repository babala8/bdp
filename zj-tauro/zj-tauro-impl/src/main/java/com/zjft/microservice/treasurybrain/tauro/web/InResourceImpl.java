package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.tauro.service.InService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuyuan
 * @since 2019/8/14 10:30
 */
@Slf4j
@RestController
public class InResourceImpl implements InResource{

	@Resource
	private InService inservice;

//	@Override
//	public DTO in(TaskTransferDTO taskTransferDTO) {
//		try {
//			return inservice.in(taskTransferDTO);
//		}catch (Exception e){
//			log.error("入库异常",e);
//			return new DTO(RetCodeEnum.EXCEPTION.getCode(),e.getMessage());
//		}
//	}
}
