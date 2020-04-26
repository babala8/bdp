package com.zjft.microservice.treasurybrain.securitycenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangjs
 * @since 2019/9/19
 */
@Data
@ApiModel(value = "泊车信息", description = "泊车信息")
public class ParkingGuideResponseDTO extends DTO {

	@ApiModelProperty(value = "泊车车牌号")
	private String parkingCarNo;

	@ApiModelProperty(value = "泊车类别")
	private String parkingCarType;

	@ApiModelProperty(value = "泊车时间")
	private String parkingCarDate;

}
