package com.zjft.microservice.treasurybrain.channelcenter.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/23 16:01
 */
@Data
public class CallCustomerLineRunDetailPO {

	private String customerNo;

	private String lineRunNo;

	private String arrivalTime;

	private String clrTimeInterval;

}
