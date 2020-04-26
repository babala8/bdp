package com.zjft.microservice.treasurybrain.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 韩通
 * @since 2019/8/7 09:08
 */
@Data
@ApiModel(value = "任务表信息",description = "任务表信息")
public class TaskTableDTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "任务单类型" ,
			example = "1-加钞任务单 2-网点调缴任务单 3-上门收款任务单 4-人行调缴任务单 5-同业现金任务单")
	private int taskType;

	@ApiModelProperty(value = "计划完成时间")
	private String planFinishDate;

	@ApiModelProperty(value = "任务状态" )
	private int status;

	@ApiModelProperty(value = "所属金库编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "线路编号")
	private String lineNo;

	@ApiModelProperty(value = "押运人员1编号")
	private String opNo1;

	@ApiModelProperty(value = "押运人员2编号")
	private String opNo2;

	@ApiModelProperty(value = "创建人员编号")
	private String createOpNo;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "预计路程")
	private int planDistance;

	@ApiModelProperty(value = "预计耗时" , notes = "单位：分钟")
	private int planTimeCost;

	@ApiModelProperty(value = "紧急标志")
	private int urgentFlag;

	@ApiModelProperty(value = "笼车编号")
	private String shelfNo;

	@ApiModelProperty(value = "加钞计划编号")
	private String addnotesPlanNo;

	@ApiModelProperty(value = "线路名称")
	private String lineName;
}
