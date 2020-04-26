package com.zjft.microservice.treasurybrain.usercenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 杨光
 * @since 2019-07-26
 */
@Data
@ApiModel(value = "加钞线路", description = "加钞线路")
public class SysPermissionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "接口编号")
	String no;

	@ApiModelProperty(value = "接口名称")
	String name;

	@ApiModelProperty(value = "接口URL")
	String url;

	@ApiModelProperty(value = "接口方法")
	String method;

	/**
	 * 0-父级菜单
	 * 1-叶子菜单
	 * 3-叶子菜单，隐藏菜单
	 */
	@ApiModelProperty(value = "接口类型")
	Integer catalog;

	@ApiModelProperty(value = "接口描述")
	String note;
}
