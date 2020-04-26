package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

@Data
public class ClrCenterTable {

	private String clrCenterNo;

	private String centerName;

	private String bankOrgNo;

	private Integer netpointMatrixStatus;

	private Integer cashtruckNum;

	private Integer autoFlag;

	private Integer centerType;

	private Integer lineMode;

	private String note;

	private Integer netpointMatrixStatusOrg;

	private Integer costMatrixPointType;

	private SysOrg sysOrg;

}
