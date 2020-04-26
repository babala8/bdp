package com.zjft.microservice.treasurybrain.task.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2020/2/29
 */
@Data
public class StorageUseCassetteBagDetailDTO {

	@ApiModelProperty(value = "属性")
	private String key;

	@ApiModelProperty(value = "值")
	private String value;

	@ApiModelProperty(value = "名称")
	private String name;
}
