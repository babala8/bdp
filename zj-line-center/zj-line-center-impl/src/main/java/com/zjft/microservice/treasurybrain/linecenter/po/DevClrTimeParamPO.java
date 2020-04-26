package com.zjft.microservice.treasurybrain.linecenter.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/17 11:11
 */

@Data
public class DevClrTimeParamPO {

	private String devNo;

	private Integer clrTimeInterval;

	private String arrivalTime;

	private Integer clrDay;

	private String addnotesLine;
}
