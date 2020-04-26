package com.zjft.microservice.treasurybrain.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wupeng
 * @since 2019/8/29
 */
@Data
@ApiModel(value = "任务金额明细",description = "任务金额明细")
public class TransferCurrencyTypeDTO {
	private String  id;

	@ApiModelProperty(value = "金额/数量/重量")
	private BigDecimal amount;

	@ApiModelProperty(value = "钞币类别")
	private Integer  currencyType;

	@ApiModelProperty(value = "币种")
	private String currencyCode;

	@ApiModelProperty(value = "面值")
	private Integer denomination;



}
