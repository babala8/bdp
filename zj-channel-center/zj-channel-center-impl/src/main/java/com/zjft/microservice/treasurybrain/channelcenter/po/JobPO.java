package com.zjft.microservice.treasurybrain.channelcenter.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/21 10:27
 */
@Data
public class JobPO {

	private int groupType;

	private String jobName;

	private int jobType;

	private int jobNumber;

	private String note;
}
