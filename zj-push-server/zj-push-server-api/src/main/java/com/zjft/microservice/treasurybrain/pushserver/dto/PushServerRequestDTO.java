package com.zjft.microservice.treasurybrain.pushserver.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 韩通
 * @since 2020-02-24
 */
@Data
@ApiModel(value = "推送请求信息", description = "推送请求信息")
public class PushServerRequestDTO extends DTO {

	@ApiModelProperty(value = "用户编号")
	private String userName;

	@ApiModelProperty(value = "角色编号",notes = "如果多个角色，用,分隔，例如：role1,role2,role3")
	private String roles;

	@ApiModelProperty(value = "推送信息")
	private String message;

	@ApiModelProperty(value = "通知类别")
	private String noticeCategory;

	@ApiModelProperty(value = "通知类别描述")
	private String noticeCategoryDescription;

	@ApiModelProperty(value = "通知信息标题")
	private String noticeTitle;

	@ApiModelProperty(value = "通知时间（用于定时发送为成功发送的消息，非定时任务不需要填写此字段）")
	private String time;

	@ApiModelProperty(value = "通知人（用于定时发送为成功发送的消息，非定时任务不需要填写此字段）")
	private String timeJobName;

	@ApiModelProperty(value = "通知地址（用于定时发送为成功发送的消息，非定时任务不需要填写此字段）")
	private String timeJobNoticeAddress;
}
