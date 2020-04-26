package com.zjft.microservice.treasurybrain.storage.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2020/3/3
 */
@Data
public class StorageCassetteBagDetailDTO extends DTO {

	@ApiModelProperty(value = "属性")
	private String key;

	@ApiModelProperty(value = "值")
	private String value;

	@ApiModelProperty(value = "名称")
	private String name;
}
