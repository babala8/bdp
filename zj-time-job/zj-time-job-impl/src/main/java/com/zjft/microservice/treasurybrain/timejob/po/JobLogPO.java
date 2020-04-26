package com.zjft.microservice.treasurybrain.timejob.po;

import lombok.Data;

/**
 * @author 张思雨
 * @since 2019-08-05
 */
@Data
public class JobLogPO {

	private String logicId;

	private String jobName;

	private Integer jobType;

	private Integer jobResult;

	private String jobCreator;

	private String startTime;

	private String endTime;

	private String resultDesc;
}
