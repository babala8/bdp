package com.zjft.microservice.treasurybrain.productcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/09/02
 */
@Data
@ApiModel(value = "商品属性",description = "商品属性")
public class ProductPropertyDTO extends DTO {
	private String id;

	@ApiModelProperty(value = "属性编号")
	private String propertyNo;

	@ApiModelProperty(value = "商品编号")
	private String productNo;

	@ApiModelProperty(value = "属性名称")
	private String propertyName;

	@ApiModelProperty(value = "属性值")
	private List<String> propertyValue;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@ApiModelProperty(value = "更新时间")
	private String updateTime;

	@ApiModelProperty(value = "属性类型")
	private Integer propertyType;
}
