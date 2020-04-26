package com.zjft.microservice.treasurybrain.linecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "加钞线路", description = "加钞线路")
public class AddnotesLineDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "线路编号")
	private String lineNo;

	@ApiModelProperty(value = "线路名称")
	private String lineName;

	@ApiModelProperty(value = "清机中心编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "加钞周期(天)", example = "3")
	private Integer addClrPeriod;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "线路类型 0-ATM加钞路线 1-网点加钞路线 2-上门收款 3-紧急加钞路线")
	private Integer lineType;

}
