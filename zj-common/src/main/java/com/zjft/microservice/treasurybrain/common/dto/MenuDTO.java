package com.zjft.microservice.treasurybrain.common.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 杨光
 */
@Getter
@Setter
@ToString
@ApiModel(value = "菜单", description = "菜单")
public class MenuDTO extends DTO {

	@ApiModelProperty(value = "菜单编号")
	private String no;

	@ApiModelProperty(value = "菜单名称")
	private String name;

	@ApiModelProperty(value = "父菜单编号")
	private String menuFather;

	@ApiModelProperty(value = "权限子菜单标志")
	private String url;

	@ApiModelProperty(value = "菜单层级")
	private Integer SysPermissionDTO;

	@ApiModelProperty(value = "菜单顺序")
	private Integer menuOrder;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "菜单图标")
	private String icon;

	@ApiModelProperty(value = "菜单大小")
	private String size;

	@ApiModelProperty(value = "菜单背景")
	private String backgroundColor;

	@ApiModelProperty(value = "按钮&接口标志")
	private String buttonTag;

	@ApiModelProperty(value = "按钮&接口")
	private String button;

	@ApiModelProperty(value = "是否被选中")
	private String checked;

	public MenuDTO() {
	}

	public MenuDTO(RetCodeEnum re) {
		super(re);
	}

}
