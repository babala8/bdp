package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "物品信息", description = "物品信息")
public class ProductDTO {

	@ApiModelProperty(value = "物品名称")
	String productName;

	@ApiModelProperty(value = "属性列表", notes = "json字符串，由 GOODS_PROPERTY_KEY表 和 GOODS_PROPERTY_VALUE表得出")
	String propertyList;

	@ApiModelProperty(value = "调拨方向")
	Integer direction;
}
