package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

@Data
public class CallCustomerTime {

	private String customerNo;

	private int clrTimeInterval;

	private int clrDay;

	private String arrivalTime;

	private String callCustomerLine;

}
