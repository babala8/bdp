package com.zjft.microservice.treasurybrain.tauro.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/08/08
 */
public interface OutStorageService {

	DTO outStorage(TaskTransferDTO taskTransferDTO);

	List<String> getLowerContainerNosList(String containerNo, List<String> list, String taskNo);
}
