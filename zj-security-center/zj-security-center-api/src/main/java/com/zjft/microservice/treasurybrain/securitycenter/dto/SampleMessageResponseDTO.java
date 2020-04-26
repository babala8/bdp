package com.zjft.microservice.treasurybrain.securitycenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangjs
 * @since 2019/9/9
 */
@Data
@ApiModel(value = "预警信息", description = "预警信息")
public class SampleMessageResponseDTO extends DTO {

	@ApiModelProperty(value = "预警信息类型")
	private String warnMessageType;

	@ApiModelProperty(value = "预警信息内容")
	private String warnMessageInfo;

	@ApiModelProperty(value = "预警信息推送人")
	private String warnMessageToUserNo;

	@ApiModelProperty(value = "预警信息推动角色")
	private String warnMessageToRoleNo;

	@ApiModelProperty(value = "清机中心编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "清机中心名称")
	private String centerName;

}
