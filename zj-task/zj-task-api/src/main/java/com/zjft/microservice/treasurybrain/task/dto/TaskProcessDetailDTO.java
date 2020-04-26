package com.zjft.microservice.treasurybrain.task.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "流转任务详情", description = "流转任务详情")
public class TaskProcessDetailDTO extends TaskProcessDTO{

	private String taskNo;

	private List<CustomerInfoDTO> customerInfoDTOList;
}
