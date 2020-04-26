package com.zjft.microservice.treasurybrain.tauro.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;

/**
 * @author 常 健
 * @since 2019/08/09
 */
public interface SignService {

	DTO sign(TaskTransferDTO taskTransferDTO);
}
