package com.zjft.microservice.treasurybrain.business.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 选择设备时使用的DTO，用于四种类型设备的选择
 * @author liuyuan
 * @since 2019/7/23 13:36
 */
@Data
@ApiModel(description = "待选择设备对象")
public class DevForChooseDTO extends DTO {

	@ApiModelProperty(name = "设备编号", dataType = "String")
	private String devNo;

	@ApiModelProperty(name = "设备类型名称", dataType = "String")
	private String devCatalogName;

	@ApiModelProperty(name = "地址", dataType = "String")
	private String address;

//	@ApiModelProperty(name = "所属机构", dataType = "SysOrg", notes = "机构编号和机构名称")
//	private SysOrg sysOrg;

	@ApiModelProperty(name = "机构编号", dataType = "String")
	private String orgNo;

	@ApiModelProperty(name = "机构名称", dataType = "String")
	private String orgName;

//	@ApiModelProperty(name = "所属线路", dataType = "AddnotesLineDTO", notes = "线路编号和线路名称")
//	private AddnotesLineDTO addnotesLine;

	@ApiModelProperty(name = "线路编号", dataType = "String")
	private String lineNo;

	@ApiModelProperty(name = "线路名称", dataType = "String")
	private String lineName;

	@ApiModelProperty(name = "事件名称", dataType = "String", notes = "事件型设备字段")
	private String keyEvent;

	@ApiModelProperty(name = "事件详情描述", dataType = "String", notes = "事件型设备字段")
	private String keyEventDetail;

	@ApiModelProperty(name = "预测优先度分值", dataType = "String", notes = "预测型设备字段")
	private BigDecimal chsEstScore;

	@ApiModelProperty(name = "辅助型分值", dataType = "String", notes = "预测型设备字段")
	private BigDecimal chsAuxScore;

	@ApiModelProperty(name = "剩余可用钞量", dataType = "String", notes = "预测型设备字段")
	private String availableAmt;

}
