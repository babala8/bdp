package com.zjft.microservice.treasurybrain.timejob.po;

import lombok.Data;

/**
 * @author 常 健
 * @since 2019/07/16
 */
@Data
public class CityInfoPO {
	private String cityNo;

	private String cityName;

	private String province;

	private String weatherId;

	private Integer clrCenterNum;

	private String note;
}
