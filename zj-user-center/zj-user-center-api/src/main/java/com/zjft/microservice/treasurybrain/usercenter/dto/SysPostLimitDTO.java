package com.zjft.microservice.treasurybrain.usercenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/11/14
 */
@Data
@ApiModel(value = "岗位制约信息", description = "岗位制约信息")
public class SysPostLimitDTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "岗位编号", example = "000001")
	private String postNo;

	@ApiModelProperty(value = "制约岗位编号", example = "000001")
	private String postLimitNo;

	@ApiModelProperty(value = "制约岗位名称", example = "制约岗位1")
	private String postLimitName;

	@ApiModelProperty(value = "备注", example = "备注")
	private String note;

}
