package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 崔耀中
 * @since 2019-11-01
 */

@Data
@ApiModel(value = "操作状态信息", description = "操作状态信息")
public class ServiceStatusExpandDTO {

	@ApiModelProperty(value = "服务对象类型")
	String operateType;

	@ApiModelProperty(value = "服务对象名称")
	String description;

	@ApiModelProperty(value = "服务对象名称")
	Integer nextStatus;

	@ApiModelProperty(value = "服务对象名称")
	String statusName;
}
