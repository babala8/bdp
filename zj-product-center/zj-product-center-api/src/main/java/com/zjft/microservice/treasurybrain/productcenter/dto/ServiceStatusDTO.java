package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "服务状态", description = "服务状态")
public class ServiceStatusDTO {

	@ApiModelProperty(value = "产品编号")
	Integer serviceNo;

	@ApiModelProperty(value = "状态码")
	Integer status;

	@ApiModelProperty(value = "状态名称")
	String name;

	@ApiModelProperty(value = "备注")
	String note;

	@ApiModelProperty(value = "节点类型")
	String weakNode;
}
