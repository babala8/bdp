package com.zjft.microservice.treasurybrain.channelcenter.po;

import lombok.Data;
import oracle.sql.CHAR;

/**
 * @author 崔耀中
 * @since 2019-11-19
 */
@Data
public class OrgBusinessTimePO {

	private String orgNo;

	private String orgTimeInterval;

	private String openTime;

	private String closeTime;

	private Integer orgDay;

}
