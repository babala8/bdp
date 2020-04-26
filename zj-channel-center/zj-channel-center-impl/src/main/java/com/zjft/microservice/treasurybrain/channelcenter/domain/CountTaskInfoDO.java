package com.zjft.microservice.treasurybrain.channelcenter.domain;

import lombok.Data;

@Data
public class CountTaskInfoDO {

	private String taskNo;

	private String devNo;

	private Short batch;

	private Short countStatus;

	private String countStartDate;

	private String countEndDate;

	private double countAmount;
}
