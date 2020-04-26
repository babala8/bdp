package com.zjft.microservice.treasurybrain.usercenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/11/19
 */
@Data
public class PostScheduleMouldOpDTO {

	@ApiModelProperty(value = "人员编号")
	private String opNo;

	@ApiModelProperty(value = "人员名称")
	private String opName;
}
