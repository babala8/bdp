package com.zjft.microservice.treasurybrain.storage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 常 健
 * @since 2019/10/11
 */
@Data
public class CheckInventoryDetailDTO {

	@ApiModelProperty(value = "面值")
	private BigDecimal denomination;

	@ApiModelProperty(value = "数量")
	private BigDecimal amount;
}
