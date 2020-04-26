package com.zjft.microservice.treasurybrain.tauro.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "容器信息（如钞箱钞袋）", description = "容器信息（如钞箱钞袋）")
public class ContainerInfoDTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "容器编号")
	private String containerNo;

	@ApiModelProperty(value = "容器类型")
	private Integer containerType;

	@ApiModelProperty(value = "容器名称")
	private String containerName;
}
