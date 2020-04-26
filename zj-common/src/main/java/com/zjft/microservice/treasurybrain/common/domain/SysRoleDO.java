package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

/**
 * @author 杨光
 */
@Data
public class SysRoleDO {

	private Integer no;

	private String name;

	private Integer catalog;

	private Integer orgGradeNo;

	private String note;

	public SysRoleDO() {
	}

	public SysRoleDO(Integer no, String name) {
		this.no = no;
		this.name = name;
	}
}
