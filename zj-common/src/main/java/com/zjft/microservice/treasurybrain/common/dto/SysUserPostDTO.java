package com.zjft.microservice.treasurybrain.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/11/12
 */
@Data
@ApiModel(value = "用户-岗位", description = "用户-岗位")
public class SysUserPostDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名", example = "zhangsan")
	private String userName;

	@ApiModelProperty(value = "岗位编号", example = "000001")
	private String postNo;
}
