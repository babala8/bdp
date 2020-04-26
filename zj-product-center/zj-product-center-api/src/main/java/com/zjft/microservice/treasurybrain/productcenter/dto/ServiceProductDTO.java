package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "产品物品信息", description = "产品物品信息")
public class ServiceProductDTO {
	@ApiModelProperty(value = "产品编号")
	Integer serviceNo;

	@ApiModelProperty(value = "物品编号")
	String productNo;

	@ApiModelProperty(value = "物品名称")
	String productName;

	@ApiModelProperty(value = "属性列表", notes = "json字符串，由 GOODS_PROPERTY_KEY表 和 GOODS_PROPERTY_VALUE表得出")
	List<String> propertyList;

	@ApiModelProperty(value = "调拨方向")
	Integer direction;

	@ApiModelProperty(value = "属性值对应ID")
	String propertyValueID;

	@ApiModelProperty(value = "属性编号")
	String propertyNo;

	@ApiModelProperty(value = "属性名称")
	String propertyName;

	@ApiModelProperty(value = "物品参数名称")
	String parameterName;

	@ApiModelProperty(value = "物品参数类型")
	String parameterType;

	@ApiModelProperty(value = "产品值信息")
	List<ProductPropertyValueDetailDTO> propertyValueDetailList;

	@ApiModelProperty(value = "参数信息")
	List<SelfServiceProductArgumentDTO> selfServiceProductArgumentDTOList;
}
