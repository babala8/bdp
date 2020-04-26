package com.zjft.microservice.treasurybrain.channelcenter.dto;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 崔耀中
 * @since 2019/9/21
 */
@Data
@ApiModel(value = "车辆信息", description = "车辆信息")
public class CarDTO extends DTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "车辆编号",example = "1001")
	private Integer carNo;

	@ApiModelProperty(value = "车辆类型 -- 0：小型 1：中型 2：大型",example = "1")
	private Integer type;

	@ApiModelProperty(value = "车牌号",example = "沪A00001")
	private String carNumber;

	@ApiModelProperty(value = "车辆状态 -- 0：故障 1：正常",example = "1")
	private Integer status;

	@ApiModelProperty(value = "公司编号",example = "10001")
	private String company;

	@ApiModelProperty(value = "签约方式 -- 0：计次 1：月付 2：里程付",example = "0")
	private Integer signingType;

	@ApiModelProperty(value = "最大时间 单位小时",example = "10")
	private String maxDuration;

	@ApiModelProperty(value = "最大里程 单位千米",example = "100")
	private String maxMileage;

	@ApiModelProperty(value = "押运公司名称", example = "深圳市东南实业有限公司(省行自定义服务商)")
	private String companyName;

}
