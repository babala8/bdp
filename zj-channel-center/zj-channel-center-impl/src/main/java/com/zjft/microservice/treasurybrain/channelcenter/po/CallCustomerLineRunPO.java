package com.zjft.microservice.treasurybrain.channelcenter.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/23 15:58
 */
@Data
public class CallCustomerLineRunPO {

	private String lineRunNo;

	private String lineNo;

	private String theYearMonth;

	private String theDay;

	private int callCustomerCount;

}
