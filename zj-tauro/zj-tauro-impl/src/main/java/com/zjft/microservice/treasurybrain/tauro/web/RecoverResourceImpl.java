package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.tauro.service.RecoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuyuan
 * @since 2019/8/14 10:41
 */
@Slf4j
@RestController
public class RecoverResourceImpl implements RecoverResource{

	@Resource
	private RecoverService recoverService;

	@Override
	public DTO recover(TaskTransferDTO taskTransferDTO) {
		try {
			return recoverService.recover(taskTransferDTO);
		}catch (Exception e){
			log.error("回收异常", e);
			return new DTO(RetCodeEnum.EXCEPTION.getCode(),e.getMessage());
		}
	}
}
