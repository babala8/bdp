package com.zjft.microservice.treasurybrain.managecenter.po;

import lombok.Data;

/**
 * @author 葛瑞莲
 * @since 2019/6/11
 */
@Data
public class TagInfoPO {
	private String tagTid;

	private String epcInfo;

	private Integer epcMemorySize;

	private Integer tagType;

	private Integer status;

	private Integer userdataMemorySize;

	private String note;

	private String clrCenterNo;

	//业务使用，无意义
	private int count;
}
