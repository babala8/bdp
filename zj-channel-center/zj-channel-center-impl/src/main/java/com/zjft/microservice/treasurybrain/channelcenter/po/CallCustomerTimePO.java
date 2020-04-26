package com.zjft.microservice.treasurybrain.channelcenter.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/17 11:11
 */

@Data
public class CallCustomerTimePO {

	private String customerNo;

	private int clrTimeInterval;

	private int clrDay;

	private String arrivalTime;

	private String callCustomerLine;

	private String customerName;

	private String address;

}
