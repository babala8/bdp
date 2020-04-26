package com.zjft.microservice.treasurybrain.channelcenter.po;

import lombok.Data;

/**
 * @author 崔耀中
 * @since 2019-09-21
 */
@Data
public class CarInfoPO {

	private Integer carNo;

	private Integer type;

	private String carNumber;

	private Integer status;

	private String company;

	private Integer signingType;

	private String maxDuration;

	private String maxMileage;

	//业务使用，无意义  导入文件
	private int count;
}
