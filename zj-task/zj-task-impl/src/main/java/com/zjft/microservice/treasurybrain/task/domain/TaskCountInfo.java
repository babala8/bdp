package com.zjft.microservice.treasurybrain.task.domain;

import lombok.Data;

@Data
public class TaskCountInfo {

	private String taskNo;

	private Integer taskType;

	private Integer taskStatus;

	private String devNo;

	private Integer batch;

	private Integer countStatus;

	private String countStartDate;

	private String countEndDate;

	private double countAmount;

	private String clrCenterNo;

	private String addnotesPlanNo;
}
