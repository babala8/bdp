package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/8/7 14:58
 */
@Data
@ApiModel(value = "容器配钞信息",description = "容器配钞信息")
public class ContainerEntityDTO extends DTO {

	@ApiModelProperty(value = "容器编号")
	private String containerNo;

	@ApiModelProperty(value = "容器类型" )
	private Integer containerType;

	@ApiModelProperty(value = "容器类型描述")
	private String containerTypeDesp;

	@ApiModelProperty(value = "物品类型:1-现金 2-贵金属 3-重空" )
	private Integer entityType;

	@ApiModelProperty(value = "调运方向")
	private Integer direction;

	@ApiModelProperty(value = "上级容器编号")
	private String upperNo;

	@ApiModelProperty(value = "是否为最下级容器" , example = "0-不是 1-是")
	private Integer leafFlag;

	@ApiModelProperty(value = "容器状态")
	private Integer status;

	@ApiModelProperty(value = "钞币类型")
	private Integer currencyType;

	@ApiModelProperty(value = "币种")
	private String currencyCode;

	@ApiModelProperty(value = "面值")
	private Integer denomination;

	@ApiModelProperty(value = "金额")
	private double amount;

	@ApiModelProperty(value = "操作类型 1-配钞 2-清点")
	private Integer opType;

	@ApiModelProperty(value = "操作人员")
	private String opNo;

	@ApiModelProperty(value = "操作人员姓名")
	private String opName;

	@ApiModelProperty(value = "清点时间")
	private String opTime;

	@ApiModelProperty(value = "清点设备编号")
	private String clearMachineNo;

}
