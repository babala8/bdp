package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "设备加钞周期List", description = "设备加钞周期List")
public class DevClrTimeParamListDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "设备编号")
	private String devNo;

	@ApiModelProperty(value = "加钞周期(天)", example = "3")
	private Integer addClrPeriod;

	@ApiModelProperty(value = "最大加钞周期(天)", example = "3")
	private Integer maxAddClrPeriod;

	private List<DevClrTimeParamDTO> timeList;

	public DevClrTimeParamListDTO(RetCodeEnum retCodeEnum){
		super(retCodeEnum);
	}

	public DevClrTimeParamListDTO(){
		super();
	}

}
