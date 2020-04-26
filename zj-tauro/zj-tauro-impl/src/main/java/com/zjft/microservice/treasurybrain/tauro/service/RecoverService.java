package com.zjft.microservice.treasurybrain.tauro.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;

/**
 * @author 常 健
 * @since 2019/08/14
 */
public interface RecoverService {

	DTO recover(TaskTransferDTO taskTransferDTO);
}
