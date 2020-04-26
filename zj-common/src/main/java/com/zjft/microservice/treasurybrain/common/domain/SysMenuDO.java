package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

/**
 * @author 杨光
 */
@Data
public class SysMenuDO {

	private String no;

	private String name;

	private String menuFather;

	private String url;

	private Integer menuLevel;

	private Integer menuOrder;

	private String note;

	private String menuIcon;

	private String menuSize;

	private String menuBg;
	/**
	 * 0-父级菜单
	 * 1-叶子菜单
	 * 3-隐藏的叶子菜单
	 */
	private String buttonTag;

	private String button;

	/**
	 * 不是SYS_MENU表中的字段，表示某个角色下的叶子菜单是否被选中
	 */
	private String checked;

	private static final long serialVersionUID = 1L;


}
