package com.zjft.microservice.treasurybrain.channelcenter.domain;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/23 16:02
 */
@Data
public class CallCustomerLineRunMonthDO {

	private String lineNo;

	private String lineName;

	private String theYearMonth;

	private Integer callCustomerNum;
}
