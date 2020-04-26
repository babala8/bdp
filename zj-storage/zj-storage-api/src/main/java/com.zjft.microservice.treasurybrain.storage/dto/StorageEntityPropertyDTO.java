package com.zjft.microservice.treasurybrain.storage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 崔耀中
 * @since 2020-03-02
 */
@Data
@ApiModel(value = "仓储属性表")
public class StorageEntityPropertyDTO {

	@ApiModelProperty(value = "编号" )
	private String id;

	@ApiModelProperty(value = "属性")
	private String key;

	@ApiModelProperty(value = "属性类型")
	private Integer propertyType;

	@ApiModelProperty(value = "属性名称")
	private String name;

	@ApiModelProperty(value = "属性值")
	private String value;

}
