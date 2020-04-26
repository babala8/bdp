package com.zjft.microservice.treasurybrain.tauro.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "流转任务", description = "流转任务")
public class TaskProcessDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务编号")
	private String taskNo;

	@ApiModelProperty(value = "任务生成日期")
	private String taskDate;

	@ApiModelProperty(value = "清机中心编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "加钞日期")
	private String addnotesDate;

	@ApiModelProperty(value = "操作人员1名称")
	private String opName1;

	@ApiModelProperty(value = "操作人员2名称")
	private String opName2;

	@ApiModelProperty(value = "加钞计划编号")
	private String addnotesPlanNo;

	@ApiModelProperty(value = "加钞时间段")
	private String clrTimeInterval;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "是否紧急任务")
	private Integer urgentFlag;

	@ApiModelProperty(value = "线路编号")
	private String lineNo;

	@ApiModelProperty(value = "线路名称")
	private String lineName;
}
