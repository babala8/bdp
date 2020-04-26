package com.zjft.microservice.treasurybrain.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "设备加钞周期", description = "设备加钞周期")
public class DevClrTimeParamDTO {

	@ApiModelProperty(value = "设备编号")
	private String devNo;

	@ApiModelProperty(value = "清机时段(1-上午,2-下午)", example = "1")
	private Integer clrTimeInterval;

	@ApiModelProperty(value = "要求到达时间(hh:mm:ss)")
	private String arrivalTime;

	@ApiModelProperty(value = "清机天数序号")
	private String clrDay;

	@ApiModelProperty(value = "所属线路")
	private String addnotesLine;

	@ApiModelProperty(value = "线路名称")
	private String lineName;

}
