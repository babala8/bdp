package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "产品属性值详细",description = "产品属性值详细")
public class ProductPropertyValueDetailDTO {

	@ApiModelProperty(value = "属性值编号")
	private String id;

	@ApiModelProperty(value = "属性key")
	private String propertyNo;

	@ApiModelProperty(value = "属性key名称")
	private String propertyName;

	@ApiModelProperty(value = "属性值")
	private String propertyValue;
}
