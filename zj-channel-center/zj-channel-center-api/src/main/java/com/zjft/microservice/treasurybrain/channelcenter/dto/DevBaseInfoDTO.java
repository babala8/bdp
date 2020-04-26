package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.domain.DevServiceCompany;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ApiModel(value = "设备基础信息", description = "设备基础信息(用于添加，修改接口的参数)")
public class DevBaseInfoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "物品编号", example = "44020001")
	private String no;

	@ApiModelProperty(value = "设备IP地址", example = "127.0.0.1")
	private String ip;
	private String areaNo;
	private String region;
	private String province;
	private String city;
	private String address;
	private String virtualTellerNo;

	@ApiModelProperty(value = "所属机构", example = "0440202117")
	private String orgNo;
	private String clrCenterNo;

	@ApiModelProperty(value = "设备型号", example = "1002")
	private String devType;

	@ApiModelProperty(value = "设备维护商", example = "10001")
	private String devService;
	private String note1;
	private String note2;
	private String note3;
	private String note4;
	private String note5;

	@ApiModelProperty(value = "经营方式 1—自营  2—联营", example = "1")
	private String workType;

	@ApiModelProperty(value = "钞箱最大加钞量单位(元)", example = "250000")
	private Integer cassetteStantardSize;

	@ApiModelProperty(value = "设备最小加钞金额单位(元)", example = "50000")
	private Integer devLeastSize;
	@ApiModelProperty(value = "设备最大加钞金额单位(元)", example = "800000")
	private Integer devStantardSize;
	private String accountNetpoint;
	private String accountNetpointName;
	private Double x;
	private Double y;
	private String devTypeCash;
	private Integer setupType;
	private String addnotesLine;
	private String addnotesLineName;

	@ApiModelProperty(value = "设备状态 1—正常  2—停机", example = "1")
	private String status;
	private String netType;

	@ApiModelProperty(value = "", example = "3")
	private Integer maxAddClrPeriod;

	@ApiModelProperty(value = "", example = "3")
	private Integer addClrPeriod;
	private String cashboxLimit;

	@ApiModelProperty(value = "离行在行标志 1：在行；2：离行", example = "1")
	private Integer awayFlag;
	private Integer cycleFlag;

	private String orgName;
	private String devTypeName;


	private DevTypeDTO devTypeTable;
	private DevServiceCompany devServiceCompany;
	private SysOrgDTO sysOrg;
}
