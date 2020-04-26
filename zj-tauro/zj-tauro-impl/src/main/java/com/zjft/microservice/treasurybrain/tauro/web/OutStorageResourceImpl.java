package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.tauro.service.OutStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 常 健
 * @since 2019/08/08
 */
@Slf4j
@RestController
public class OutStorageResourceImpl implements OutStorageResource {

	@Resource
	private OutStorageService outStorageService;

	@Override
	public DTO outStorage(TaskTransferDTO taskTransferDTO) {
		try {
			return outStorageService.outStorage(taskTransferDTO);
		}catch (Exception e){
			log.error("物品出库异常",e);
			return new DTO(RetCodeEnum.EXCEPTION.getCode(),e.getMessage());
		}
	}
}
