package com.zjft.microservice.treasurybrain.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "线路运行图设备信息", description = "线路运行图设备信息")
public class LineRunDevDetailDTO {

	@ApiModelProperty(value = "设备号")
	private String devNo;

	@ApiModelProperty(value = "线路运行编号")
	private String lineRunNo;

	@ApiModelProperty(value = "设备所在网点号")
    private String orgNo;

	@ApiModelProperty(value = "设备所在网点名称")
    private String orgName;

	@ApiModelProperty(value = "清机时段")
    private String clrTimeInterval;

	@ApiModelProperty(value = "要求到达网点时间")
    private String arrivalTime;

}
