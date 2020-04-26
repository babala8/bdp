package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "产品物品信息", description = "产品物品信息")
public class SelfServiceProductArgumentDTO {

	@ApiModelProperty(value = "id")
	String id;

	@ApiModelProperty(value = "物品编号")
	String productNo;

	@ApiModelProperty(value = "物品参数")
	String parameter;

	@ApiModelProperty(value = "参数名称")
	String parameterName;

	@ApiModelProperty(value = "参数类型")
	String parameterType;


}
