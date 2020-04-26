package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangjs
 * @since 2019/10/10 15:24
 */

@Data
@ApiModel(value = "任务配钞清分信息",description = "任务配钞清分信息")
public class TaskCountInfoDTO extends DTO {

	@ApiModelProperty(value = "任务编号")
	private String taskNo;

	@ApiModelProperty(value = "任务种类")
	private Integer taskType;

	@ApiModelProperty(value = "任务进度状态")
	private Integer taskStatus;

	@ApiModelProperty(value = "清分配钞设备")
	private String devNo;

	@ApiModelProperty(value = "清分配钞批次")
	private Integer batch;

	@ApiModelProperty(value = "清分配钞状态")
	private Integer countStatus;

	@ApiModelProperty(value = "清分配钞开始时间")
	private String countStartDate;

	@ApiModelProperty(value = "清分配钞结束时间")
	private String countEndDate;

	@ApiModelProperty(value = "清分配钞金额")
	private double countAmount;

	@ApiModelProperty(value = "清分中心编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "计划编号")
	private String addnotesPlanNo;

}
