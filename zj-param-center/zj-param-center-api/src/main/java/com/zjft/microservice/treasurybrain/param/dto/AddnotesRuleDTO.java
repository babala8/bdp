package com.zjft.microservice.treasurybrain.param.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "特殊规则", description = "特殊规则")
public class AddnotesRuleDTO extends DTO {
	private static final long serialVersionUID = 1L;

	public AddnotesRuleDTO() {

	}

	public AddnotesRuleDTO(RetCodeEnum r) {
		super(r);
	}

	@ApiModelProperty(value = "规则编号")
	private String ruleId;

	@ApiModelProperty(value = "规则名称")
	private String ruleDesp;

	@ApiModelProperty(value = "规则生成人员编号")
	private String ruleGenOpNo;

	@ApiModelProperty(value = "规则生成人员名称")
	private String ruleGenOpName;

	@ApiModelProperty(value = "规则生成日期")
	private String ruleGenDate;

	@ApiModelProperty(value = "规则生成时间")
	private String ruleGenTime;

	@ApiModelProperty(value = "配钞系数", example = "1")
	private Double addnotesCoeff;

	@ApiModelProperty(value = "定额配比", example = "1")
	private Double quotaRatio;

	@ApiModelProperty(value = "加钞周期", example = "1")
	private Double addnotesPeriod;

	@ApiModelProperty(value = "清机中心编号")
	private String clrCenterNo;

}
