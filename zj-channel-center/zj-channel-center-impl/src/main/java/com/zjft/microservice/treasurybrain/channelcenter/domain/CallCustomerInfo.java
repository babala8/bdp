package com.zjft.microservice.treasurybrain.channelcenter.domain;

import lombok.Data;

@Data
public class CallCustomerInfo {

	private String customerNo;

	private String customerShortName;

	private String address;

	private String location;

	private String x;

	private String y;

	private String isOneself;

	private String customerNumber;

	private String cnCustomerLongName;

	private String customerAuthPhone;

    private String customerManage;

    private String touchPhoneOne;

    private String touchPhoneTwo;

    private String callCustomerLine;

    private Integer callClrPeriod;

    private String callCustomerLineName;

	private String callCustomerType;

	private String callCustomerTypeName;

}
