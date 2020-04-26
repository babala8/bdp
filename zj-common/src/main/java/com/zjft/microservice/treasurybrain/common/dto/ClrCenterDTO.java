package com.zjft.microservice.treasurybrain.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ClrCenterDTO extends DTO {

	@ApiModelProperty(value = "清机中心编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "清机中心名称")
	private String centerName;

	@ApiModelProperty(value = "机构编号")
	private String bankOrgNo;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "自动化标识",example = "0-非自动化 1-自动化")
	private Integer autoFlag;

	@ApiModelProperty(value = "金库类型",example = "0-总库 1-业务库 2-营运库 3-代理库 4-黄金交割库 5-备用金库")
	private Integer centerType;

	@ApiModelProperty(value = "线路模式", example = "1-自动规划2-固定线路")
	private Integer lineMode;

	private Integer netpointMatrixStatus;

	private Integer cashtruckNum;

	private Integer netpointMatrixStatusOrg;

	private Integer costMatrixPointType;

	private SysOrgDTO sysOrg;

}
