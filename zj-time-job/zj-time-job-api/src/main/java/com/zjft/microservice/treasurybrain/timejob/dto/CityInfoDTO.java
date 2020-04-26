package com.zjft.microservice.treasurybrain.timejob.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/07/16
 */

@Data
public class CityInfoDTO extends DTO {

	@ApiModelProperty(value = "城市编号")
	private String cityNo;

	@ApiModelProperty(value = "城市名称")
	private String cityName;

	@ApiModelProperty(value = "省份")
	private String province;

	@ApiModelProperty(value = "获取天气对应的ID")
	private String weatherId;

	@ApiModelProperty(value = "金库个数")
	private Integer clrCenterNum;

	@ApiModelProperty(value = "备注")
	private String note;
}
