package com.zjft.microservice.treasurybrain.usercenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户操作日志", description = "用户操作日志")
public class SysWebLogDTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名称")
	private String username;

	@ApiModelProperty(value = "进程ID")
	private String tid;

	@ApiModelProperty(value = "方法类型")
	private String method;

	@ApiModelProperty(value = "请求地址")
	private String url;

	@ApiModelProperty(value = "请求开始时间")
	private String startTime;

	@ApiModelProperty(value = "请求花费时间")
	private String costTime;

	@ApiModelProperty(value = "请求结果")
	private String result;

	@ApiModelProperty(value = "客户机IP")
	private String clientIP;

	@ApiModelProperty(value = "服务器IP")
	private String serverIP;

	@ApiModelProperty(value = "请求具体描述")
	private String description;

	@ApiModelProperty(value = "用户中文名")
	private String name;

}
