package com.zjft.microservice.treasurybrain.clearcenter.domain;

import lombok.Data;

@Data
public class TaskCountInfoDO {

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
