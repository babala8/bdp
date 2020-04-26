package com.zjft.microservice.treasurybrain.usercenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author 张弛
 * @since 2020-1-13
 *
 */

@Data
@ApiModel(value = "菜单列表", description = "菜单列表")
public class SysMenuDTO extends DTO {

	@ApiModelProperty(value = "菜单编号")
	String no;

	@ApiModelProperty(value = "菜单名称")
	String name;

	@ApiModelProperty(value = "父菜单编号")
	String menuFather;

	@ApiModelProperty(value = "父菜单名称")
	String menuFatherName;

	@ApiModelProperty(value = "路由")
	String url;

	@ApiModelProperty(value = "图标")
	String menuIcon;

	@ApiModelProperty(value = "菜单层次")
	Integer menuLevel;

	@ApiModelProperty(value = "菜单顺序")
	Integer menuOrder;

	@ApiModelProperty(value = "菜单大小")
	Integer menuSize;

	@ApiModelProperty(value = "背景颜色")
	String menuBg;

	@ApiModelProperty(value = "类型")
	Integer buttonTag;

	@ApiModelProperty(value = "备注")
	String note;

	@ApiModelProperty(value = "按钮")
	String button;

	@ApiModelProperty(value = "接口")
	List<String> permissionList;


	private List<MenusDTO> sysMenuList;


	public SysMenuDTO(){

	}
	public SysMenuDTO(RetCodeEnum re){
		super(re);
	}


}
