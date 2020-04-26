package com.zjft.microservice.treasurybrain.tauro.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderCoordsDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TaskProcessDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TaskProcessDetailDTO;

import java.util.Map;

public interface TaskProcessService {

	PageDTO<TaskProcessDTO> queryDispatchByPage(Map<String, Object> paramMap);

	TaskProcessDetailDTO queryDispatchDetail(String taskNo);

	DTO addTagReaderCoordsInfo(TagReaderCoordsDTO tagReaderCoordsDTO);

	ListDTO<TagReaderCoordsDTO> queryCoordsByTaskNo(String taskNo);

}
