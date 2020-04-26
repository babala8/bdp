package com.zjft.microservice.treasurybrain.storage.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2020/2/29
 */
@Data
public class StorageCassetteBagDetailDO {

	@ApiModelProperty(value = "属性")
	private String key;

	@ApiModelProperty(value = "值")
	private String value;

	@ApiModelProperty(value = "名称")
	private String name;
}
