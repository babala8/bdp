package com.zjft.microservice.treasurybrain.common.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 杨光
 */
@Getter
@Setter
@ToString
@ApiModel(value = "机构", description = "机构")
public class SysOrgDTO extends DTO {

	@ApiModelProperty(value = "机构编号")
	private String no;

	@ApiModelProperty(value = "机构名称")
	private String name;

	@ApiModelProperty(value = "父机构")
	private String parentOrgNo;
	private SysOrgDTO parentOrg;
	private Integer left;
	private Integer right;
	private String moneyorgFlag;

	@ApiModelProperty(value = "机构等级编号")
	private Integer orgGradeNo;

	private SysOrgGradeDTO orgGrade;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "区域")
	private String region;

	@ApiModelProperty(value = "城市")
	private String city;

	@ApiModelProperty(value = "坐标x")
	private String x;

	@ApiModelProperty(value = "坐标y")
	private String y;

	@ApiModelProperty(value = "联系人")
	private String linkman;

	@ApiModelProperty(value = "地址")
	private String address;

	@ApiModelProperty(value = "电话")
	private String telephone;

	@ApiModelProperty(value = "手机")
	private String mobile;

	@ApiModelProperty(value = "传真")
	private String fax;

	@ApiModelProperty(value = "邮件")
	private String email;

	private String businessRange;
	private String cupAreaCode;
	private String addressCode;
	private String areaNo;
	private String areaType;
	private String orgPhysicsCatalog;
	private String clrCenterNo;
	private ClrCenterDTO clrCenter;
	private Integer clrCenterFlag;
	private String clrCenterNoCash;
	private String shortName;
	private String fullName;
	@ApiModelProperty(value = "是否在行")
	private Integer awayFlag;

	@ApiModelProperty(value = "备注")
	private String note;

	private String hasChildren;

	@ApiModelProperty(value = "要求送达时间")
	private String deliveryTime;

	@ApiModelProperty(value = "回库时间")
	private String backTime;


	public SysOrgDTO() {
	}

	public SysOrgDTO(RetCodeEnum re) {
		super(re);
	}

}
