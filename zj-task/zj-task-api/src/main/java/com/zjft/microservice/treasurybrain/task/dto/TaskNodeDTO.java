package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "任务节点信息",description = "任务节点信息")
public class TaskNodeDTO extends DTO {

	@ApiModelProperty(value = "任务节点编号")
	private String taskNodeNo;

	@ApiModelProperty(value = "任务编号")
	private String taskNo;

	@ApiModelProperty(value = "操作人")
	private String opNo;

	@ApiModelProperty(value = "完成时间")
	private String finishTime;

	@ApiModelProperty(value = "当前节点")
	private int curNode;

	@ApiModelProperty(value = "操作类型")
	private String operateType;

	@ApiModelProperty(value = "上一节点")
	private int beforeNode;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "操作人姓名")
	private String opName;

	@ApiModelProperty(value = "上一节点描述")
	private String beforeNodeName;

	@ApiModelProperty(value = "当前节点描述")
	private String curNodeName;

	@ApiModelProperty(value = "操作类型描述")
	private String operateTypeName;

	@ApiModelProperty(value = "操作日志信息")
	private String operateValue;
}
