package com.zjft.microservice.treasurybrain.pushserver.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/09/26
 */
@Data
public class PushSeverInfoDTO extends DTO {

	@ApiModelProperty(value = "序号")
	private String no;

	@ApiModelProperty(value = "推送时间")
	private String time;

	@ApiModelProperty(value = "推送人员")
	private String name;

	@ApiModelProperty(value = "推送信息")
	private String message;

	@ApiModelProperty(value = "通知方式：1、websocket 2、短信 3、邮件")
	private Integer noticeWay;

	@ApiModelProperty(value = "通知类别")
	private String noticeCategory;

	@ApiModelProperty(value = "通知类别描述")
	private String noticeCategoryDescription;

	@ApiModelProperty(value = "通知标题")
	private String noticeTitle;

	@ApiModelProperty(value = "通知地址")
	private String noticeAddress;

	@ApiModelProperty(value = "是否接受到信息标志位：1、已接收到通知 0、未接收到通知")
	private String noticeFlag;
}
