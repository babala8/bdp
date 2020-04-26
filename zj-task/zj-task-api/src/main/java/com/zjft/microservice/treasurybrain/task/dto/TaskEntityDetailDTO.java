package com.zjft.microservice.treasurybrain.task.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TaskEntityDetailDTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键编号")
	private String id;

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "属性")
	private String key;

	@ApiModelProperty(value = "值")
	private String value;

	@ApiModelProperty(value = "名称")
	private String name;

}