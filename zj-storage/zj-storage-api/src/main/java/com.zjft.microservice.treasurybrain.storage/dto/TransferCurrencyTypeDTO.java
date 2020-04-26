package com.zjft.microservice.treasurybrain.storage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wupeng
 * @since 2019/8/29
 */
@Data
@ApiModel(value = "任务金额明细",description = "任务金额明细")
public class TransferCurrencyTypeDTO {

	@ApiModelProperty(value = "主键编号")
	private String id;

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "属性")
	private String key;

	@ApiModelProperty(value = "值")
	private String value;

	@ApiModelProperty(value = "名称")
	private String name;

}
