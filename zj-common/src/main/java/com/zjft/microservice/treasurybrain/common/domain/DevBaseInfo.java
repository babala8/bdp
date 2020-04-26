package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;


@Data
public class DevBaseInfo {
	private String no;

	private String ip;

	private String orgNo;

	private Integer awayFlag;

	private String devType;

	private String workType;

	private String status;

	private String devService;

	private String address;

	private String areaNo;

	private Double x;

	private Double y;

	private String cashboxLimit;

	private String virtualTellerNo;

	private Integer setupType;

	private String netType;

	private Integer cassetteStantardSize;

	private Integer devLeastSize;

	private Integer devStantardSize;

	private Integer addClrPeriod;

	private Integer maxAddClrPeriod;

	private String note1;

	private String note2;

	private String note3;

	private String note4;

	private String note5;

	private Short feedback;

	private Integer cycleFlag;

	private String city;

	private String region;

	private String clrCenterNo;

	private String tagTid;

	private String groupNo;

	private String province;

	private String accountNetpoint;

	private String devTypeCash;

	private Integer distributionArea;

	private Integer regLocation;

	private String townandcountryFalg;

	private String belongRegion;

	private String cooperativeEnterprise;

	private String industry;

	private String headBankAddnotesLine;

	private SysOrg sysOrg;

	private DevTypeTable devTypeTable;

	private DevServiceCompany devServiceCompany;

	private DevStatusTable devStatusTable;

	private String addnotesLine;


	// 关联字段
	private String addnotesLineName;

	private String accountNetpointName;

	private String addFlag;
}
