package com.zjft.microservice.treasurybrain.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("任务内物品表信息")
public class TaskEntityTableDTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务编号")
	private String taskNo;

	@ApiModelProperty(value = "容器编号")
	private String containerNo;

	@ApiModelProperty(value = "服务对象编号")
	private String customerNo;

	@ApiModelProperty(value = "货物类型 1-现金 2-贵金属 3-重空")
	private Integer entityType;

	@ApiModelProperty(value = "调拨方向 1-出库 2-入库")
	private Integer direction;

	@ApiModelProperty(value = "上次容器编号")
	private String upperNo;

	@ApiModelProperty(value = "是否为最下级容器:0-否 1-是")
	private Integer leafFlag;

	@ApiModelProperty(value = "容器状态")
	private Integer status;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "寄库类型 1-长寄库 2-短寄库")
	private Integer depositType;
}
