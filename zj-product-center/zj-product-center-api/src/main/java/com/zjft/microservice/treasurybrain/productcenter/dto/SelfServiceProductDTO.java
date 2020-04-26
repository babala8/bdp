package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "产品物品信息", description = "产品物品信息")
public class SelfServiceProductDTO {
	@ApiModelProperty(value = "产品编号")
	Integer serviceNo;

	@ApiModelProperty(value = "物品编号")
	String productNo;

	@ApiModelProperty(value = "物品名称")
	String productName;

	@ApiModelProperty(value = "属性列表", notes = "json字符串，由 GOODS_PROPERTY_KEY表 和 GOODS_PROPERTY_VALUE表得出")
	String propertyList;

	@ApiModelProperty(value = "调拨方向")
	Integer direction;

	@ApiModelProperty(value = "参数信息")
	List<SelfServiceProductArgumentDTO> selfServiceProductArgumentDTOList;
}
