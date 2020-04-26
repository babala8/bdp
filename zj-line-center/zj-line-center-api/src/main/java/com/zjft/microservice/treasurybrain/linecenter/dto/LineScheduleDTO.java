package com.zjft.microservice.treasurybrain.linecenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "线路运行图设备信息", description = "线路运行图设备信息")
public class LineScheduleDTO {

	@ApiModelProperty(value = "服务对象编号")
	private String customerNo;

	@ApiModelProperty(value = "服务对象类型")
	private Integer customerType;

	@ApiModelProperty(value = "线路运行编号")
	private String lineWorkId;

	@ApiModelProperty(value = "清机时段")
    private String clrTimeInterval;

	@ApiModelProperty(value = "顺序")
	private Integer sortNo;

	@ApiModelProperty(value = "要求到达网点时间")
    private String arrivalTime;

	@ApiModelProperty(value = "服务对象名称")
	private String customerName;

	@ApiModelProperty(value = "所属机构编号")
	private String orgNo;

	@ApiModelProperty(value = "所属机构名称")
	private String orgName;

	@ApiModelProperty(value = "地址")
	private String address;

}
