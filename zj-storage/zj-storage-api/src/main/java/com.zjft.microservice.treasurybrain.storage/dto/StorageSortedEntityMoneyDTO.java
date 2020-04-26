package com.zjft.microservice.treasurybrain.storage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/8/30 16:54
 */

@Data
@ApiModel(value = "库房调入调出现金")
public class StorageSortedEntityMoneyDTO {

	@ApiModelProperty(value = "币种" , example = "CNY")
	private String currencyCode;

	@ApiModelProperty(value = "钞币类型" , example = "1")
	private Integer currencyType;

	@ApiModelProperty(value = "面值" , example = "1")
	private Integer denomination;

	@ApiModelProperty(value = "金额/元" ,example = "1000000")
	private Double amount;

}
