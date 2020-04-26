package com.zjft.microservice.treasurybrain.business.domain;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/23 18:52
 */
@Data
public class CallCustomerLineRunDetailDO {
	private String lineNo;

	private String customerNo;

	private String customerNumber;

	private String lineRunNo;

	private String arrivalTime;

	private Integer clrTimeInterval;

}
