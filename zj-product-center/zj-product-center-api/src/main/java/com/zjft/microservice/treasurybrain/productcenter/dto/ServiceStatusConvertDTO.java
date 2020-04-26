package com.zjft.microservice.treasurybrain.productcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "流程转换关系", description = "流程转换关系")
public class ServiceStatusConvertDTO {

	@ApiModelProperty(value = "产品编号")
	Integer serviceNo;

	@ApiModelProperty(value = "当前状态")
	Integer curStatus;

	@ApiModelProperty(value = "当前状态名称")
	String curStatusName;

	@ApiModelProperty(value = "下一状态")
	Integer nextStatus;

	@ApiModelProperty(value = "下一状态名称")
	String nextStatusName;

	@ApiModelProperty(value = "操作")
	String operateType;

	@ApiModelProperty(value = "操作名称")
	String operateName;

	@ApiModelProperty(value = "描述")
	String description;

	@ApiModelProperty(value = "所属模块")
	String moduleName;

	@ApiModelProperty(value = "所属模块NO")
	Integer moduleNo;

	@ApiModelProperty(value = "所属服务")
	String serviceName;

	@ApiModelProperty(value = "是否弱操作")
	Integer weakNode;

	@ApiModelProperty(value = "是否弱操作")
	String template;

	List<ServiceStatusConvertDTO> serviceStatusConvertDTOS;

	List<ServiceStatusDTO> serviceStatusDTOS;
}
