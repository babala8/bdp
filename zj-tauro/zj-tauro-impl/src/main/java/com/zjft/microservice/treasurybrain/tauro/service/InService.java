package com.zjft.microservice.treasurybrain.tauro.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.TaskTransferDTO;

import java.sql.DataTruncation;
import java.util.List;

/**
 * @author 常 健
 * @since 2019/08/16
 */
public interface InService {

	DTO in(TaskTransferDTO taskTransferDTO);

	List<String> getBackContainerNosList(String containerNo, List<String> list, String taskNo);
}
