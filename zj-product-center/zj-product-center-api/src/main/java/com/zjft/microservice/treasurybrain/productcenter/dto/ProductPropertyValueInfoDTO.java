package com.zjft.microservice.treasurybrain.productcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "SKU信息管理", description = "SKU信息管理")
public class ProductPropertyValueInfoDTO extends DTO {

	@ApiModelProperty(value = "属性值")
	private List<String> propertyValue;

	public ProductPropertyValueInfoDTO() {
	}

	public ProductPropertyValueInfoDTO(RetCodeEnum re) {
		super(re);
	}
}
