package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "服务对象信息", description = "服务对象信息")
public class CustomerTypeDTO {

	@ApiModelProperty(value = "服务对象类型")
	Integer customerType;

	@ApiModelProperty(value = "服务对象名称")
	String name;
}
