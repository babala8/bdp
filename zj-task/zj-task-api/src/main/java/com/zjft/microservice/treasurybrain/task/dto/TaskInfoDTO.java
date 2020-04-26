package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/7 09:08
 */
@Data
@ApiModel(value = "任务信息",description = "任务信息")
public class TaskInfoDTO extends DTO {

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "任务单类型" ,
			example = "1-加钞任务单 2-网点调缴任务单 3-上门收款任务单 4-人行调缴任务单 5-同业现金任务单")
	private Integer taskType;

	@ApiModelProperty(value = "操作类型")
	private String operateType;

	@ApiModelProperty(value = "任务单类型名称")
	private String taskTypeName;

	@ApiModelProperty(value = "服务对象编号")
	private String customerNo;

	@ApiModelProperty(value = "服务对象类型")
	private String customerType;

	@ApiModelProperty(value = "服务对象名称")
	private String customerName;

	@ApiModelProperty(value = "计划完成时间")
	private String planFinishDate;

	@ApiModelProperty(value = "任务状态" )
	private Integer status;

	@ApiModelProperty(value = "任务状态描述" )
	private String statusDesp;

	@ApiModelProperty(value = "所属金库编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "所属金库名称")
	private String clrCenterName;

	@ApiModelProperty(value = "线路编号")
	private String lineNo;

	@ApiModelProperty(value = "线路名称")
	private String lineName;

	@ApiModelProperty(value = "押运人员1编号")
	private String opNo1;

	@ApiModelProperty(value = "押运人员1姓名")
	private String opName1;

	@ApiModelProperty(value = "押运人员2编号")
	private String opNo2;

	@ApiModelProperty(value = "押运人员2姓名")
	private String opName2;

	@ApiModelProperty(value = "创建人员编号")
	private String createOpNo;

	@ApiModelProperty(value = "创建人员姓名")
	private String createOpName;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "加钞计划编号")
	private String addnotesPlanNo;

	@ApiModelProperty(value = "紧急标志")
	private Integer urgentFlag;

	@ApiModelProperty(value = "笼车编号")
	private String shelfNo;

	@ApiModelProperty(value = "服务对象名称")
	private String orgName;

	@ApiModelProperty(value = "调入调出总金额")
	private String depositBoxAmount;

	@ApiModelProperty(value = "运行线路编号")
	private String lineWorkId;

	@ApiModelProperty(value = "服务对象地址")
	private String address;

	private List<String> list = new ArrayList<>();

	public void setList(List<String> list) {
		this.list = list;
	}

	public TaskInfoDTO() {
	}

	public TaskInfoDTO(RetCodeEnum re) {
		super(re);
	}
}
