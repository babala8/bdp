package com.zjft.microservice.treasurybrain.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskDTO  extends DTO  {
	private String taskNo;

	private BigDecimal taskType;

	private String addnotesPlanNo;

	private String planFinishDate;

	private BigDecimal status;

	private String clrCenterNo;

	private String carNumber;

	private String lineNo;

	private String opNo1;

	private String opNo2;

	private String createOpNo;

	private String createTime;

	private String note;

	private String modeOp;

	private String modeTime;

	private String modeNote;

	private String auditOp;

	private String auditTime;

	private String auditNote;

	private BigDecimal planDistance;

	private BigDecimal planTimeCost;

	private BigDecimal urgentFlag;
}
