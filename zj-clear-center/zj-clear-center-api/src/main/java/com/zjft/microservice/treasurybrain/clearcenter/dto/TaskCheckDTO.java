package com.zjft.microservice.treasurybrain.clearcenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "容器清点信息",description = "容器清点信息")
public class TaskCheckDTO {
	@ApiModelProperty(value = "记录编号")
	private String id;

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "服务对象编号")
	private String customerNo;

	@ApiModelProperty(value = "容器编号")
	private String containerNo;

	@ApiModelProperty(value = "清点人编号")
	private String opNo;

	@ApiModelProperty(value = "清点人姓名")
	private String opName;

	@ApiModelProperty(value = "清点时间")
	private String opTime;

	@ApiModelProperty(value = "清点设备")
	private String clearMachineNo;

	@ApiModelProperty(value = "长短款标志 1-长款  2-短款  3-持平")
	private BigDecimal cashShortOver;

	@ApiModelProperty(value = "操作类型")
	private String operateType;

	@ApiModelProperty(value = "服务编号")
	private Integer serviceNo;

	@ApiModelProperty(value = "任务单状态")
	private Integer status;

	@ApiModelProperty(value = "任务单类型")
	private Integer taskType;

	@ApiModelProperty(value = "推送消息类型")
	private String Type;

	@ApiModelProperty(value = "金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "币种")
	private String currencyCode;

	@ApiModelProperty(value = "面值")
	private Integer currencyType;

	@ApiModelProperty(value = "钞币类别")
	private Integer denomination;

	List<CurrencyTypeListDTO> currencyTypeList;

}
