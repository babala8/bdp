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
public class SecurityMessageResponseDTO extends SampleMessageResponseDTO {

	@ApiModelProperty(value = "预警信息编号")
	private String warnMessageId;

	@ApiModelProperty(value = "预警信息日期")
	private String warnMessageDate;

	@ApiModelProperty(value = "预警信息时间")
	private String warnMessageTime;

	@ApiModelProperty(value = "预警信息明细")
	private String warnMessageDetailInfo;

	@ApiModelProperty(value = "预警信息处理状态")
	private String warnMessageHandleStatus;

	@ApiModelProperty(value = "预警信息处理人编号")
	private String warnMessageHandleUserNo;

	@ApiModelProperty(value = "预警信息处理人姓名")
	private String warnMessageHandleUserName;

	@ApiModelProperty(value = "预警信息处理时间")
	private String warnMessageHandleDate;

	@ApiModelProperty(value = "预警信息处理结果")
	private String warnMessageHandleResult;

}
