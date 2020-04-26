package com.zjft.microservice.treasurybrain.business.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 韩 通
 * @since 2020-02-25
 */
@Data
@ApiModel(value = "属性信息" ,description = "属性信息")
public class PropertyDTO extends DTO {

	@ApiModelProperty(value = "属性编号")
	private String key;

	@ApiModelProperty(value = "属性名称")
	private String name;

	@ApiModelProperty(value = "属性值")
	private String value;
}
