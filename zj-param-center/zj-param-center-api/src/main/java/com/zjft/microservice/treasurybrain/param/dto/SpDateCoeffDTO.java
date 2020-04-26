package com.zjft.microservice.treasurybrain.param.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "金库特殊日期配钞信息", description = "金库特殊日期配钞信息")
public class SpDateCoeffDTO extends DTO {

	@ApiModelProperty(value = "开始日期[yyyy-MM-dd]")
	String startDate;

	@ApiModelProperty(value = "结束日期[yyyy-MM-dd]")
	String endDate;

	@ApiModelProperty(value = "清机中心编号")
	String clrCenterNo;

	@ApiModelProperty(value = "定额配比", example = "1")
	Double addnotesCoeff;

}
