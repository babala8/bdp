package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/09/04
 */
@Data
@ApiModel(value = "商品属性值",description = "商品属性值")
public class ProductPropertyKeyValueDTO {

	@ApiModelProperty(value = "属性编号")
	private String propertyNo;

	@ApiModelProperty(value = "属性名称")
	private String propertyName;

	@ApiModelProperty(value = "属性类型")
	private Integer propertyType;

	@ApiModelProperty(value = "属性值")
	private List<String> propertyValue;

	@ApiModelProperty(value = "属性值详细")
	private List<ProductPropertyValueDetailDTO> propertyValueDetail;

	@ApiModelProperty(value = "产品编号")
	private String productNo;

	@ApiModelProperty(value = "产品编号")
	private String productName;

	private String createTime;

	private String updateTime;
}
