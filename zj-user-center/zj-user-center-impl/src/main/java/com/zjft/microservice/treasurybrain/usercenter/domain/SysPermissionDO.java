package com.zjft.microservice.treasurybrain.usercenter.domain;

import lombok.Data;

/**
 * @author 张弛
 */
@Data
public class SysPermissionDO {
	private String no;

	private String name;

	private String url;

	private String method;

	private Integer catalog;

	private String note;
}
