package com.zjft.microservice.treasurybrain.channelcenter.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 崔耀中
 * @since 2019-10-10
 */
@Data
public class DevCountPO {

	private String devNo;

	private Integer devType;

	private String clrCenterNo;

	private Integer devStatus;

	private String devModel;

	private String devStandards;

	private String userDate;

	private String location;

	private String note;

	private String ip;
}
