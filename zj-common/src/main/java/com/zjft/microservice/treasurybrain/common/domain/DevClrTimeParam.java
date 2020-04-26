package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

@Data
public class DevClrTimeParam {

	private String devNo;

	private String clrTimeInterval;

	private String arrivalTime;

	private int clrDay;

	private String addnotesLine;
}
