package com.zjft.microservice.treasurybrain.tauro.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 葛瑞莲
 * @since 2019/8/26
 */
@Data
@ApiModel(description = "库房物品信息",value = "库房物品信息")
public class CashStorageEntityDTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "所属金库编号",example = "028001")
	private String clrCenterNo;

	@ApiModelProperty(value = "所属金库名称")
	private String clrCenterName;

	@ApiModelProperty(value = "笼车编号")
	private String shelfNo;

	@ApiModelProperty(value = "容器编号")
	private String containerNo;

	@ApiModelProperty(value = "容器类型" )
	private String containerType;

	@ApiModelProperty(value = "实物类型 1-现金 2-贵金属 3-重空" )
	private String entityType;

	@ApiModelProperty(value = "库存金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "币种")
	private String currencyCode;

	@ApiModelProperty(value = "面值")
	private Integer denomination;

	@ApiModelProperty(value = "钞币类别")
	private Integer currencyType;

}
