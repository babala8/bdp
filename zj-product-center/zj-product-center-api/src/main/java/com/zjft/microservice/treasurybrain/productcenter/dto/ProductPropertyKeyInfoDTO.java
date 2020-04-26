package com.zjft.microservice.treasurybrain.productcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "SKU信息管理", description = "SKU信息管理")
public class ProductPropertyKeyInfoDTO extends DTO {

	@ApiModelProperty(value = "属性编号")
	private String propertyNo;

	@ApiModelProperty(value = "属性名称")
	private List<String> propertyName;
}
