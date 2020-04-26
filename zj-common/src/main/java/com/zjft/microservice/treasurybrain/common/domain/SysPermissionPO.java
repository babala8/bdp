package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

@Data
public class SysPermissionPO {

	String no;

	String name;

	String url;

	String method;

	/**
	 * 0-父级菜单
	 * 1-叶子菜单
	 * 3-叶子菜单，隐藏菜单
	 */
	Integer catalog;

	String note;
}
