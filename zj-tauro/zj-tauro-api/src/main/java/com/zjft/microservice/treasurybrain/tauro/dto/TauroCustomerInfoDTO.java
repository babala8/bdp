package com.zjft.microservice.treasurybrain.tauro.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "服务对象信息（如ATM）", description = "服务对象信息（如ATM）")
public class TauroCustomerInfoDTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "服务对象编号")
	private String customerNo;

	@ApiModelProperty(value = "服务对象类型")
	private Integer customerType;

	@ApiModelProperty(value = "服务对象名称")
	private String customerName;

	@ApiModelProperty(value = "X坐标")
	private String positionX;

	@ApiModelProperty(value = "Y坐标")
	private String positionY;

	@ApiModelProperty(value = "设备状态")
	private Integer status;

	@ApiModelProperty(value = "设备地址")
	private String address;

	@ApiModelProperty(value = "设备加钞顺序")
	private String sortNo;

	@ApiModelProperty(value = "花费时间")
	private String timeCost;

	@ApiModelProperty(value = "服务对象下属容器")
	private List<ContainerInfoDTO> containerInfoDTOList;
}
