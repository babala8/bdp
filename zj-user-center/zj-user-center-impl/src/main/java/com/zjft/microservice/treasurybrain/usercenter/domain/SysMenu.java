package com.zjft.microservice.treasurybrain.usercenter.domain;

import com.zjft.microservice.treasurybrain.usercenter.dto.MenusDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysMenuDTO;
import com.zjft.microservice.treasurybrain.usercenter.po.SysMenuPO;
import lombok.Data;

import java.util.List;

/**
 * @author 张弛
 */
@Data
public class SysMenu extends SysMenuPO {

	private String no;

	private String name;

	private String menuFather;

	/**
	 * 不是SYS_MENU表中的字段 关联字段
	 */

	private String menuFatherName;

	private String url;

	private Integer menuLevel;

	private Integer menuOrder;

	private String note;

	private String menuIcon;

	private Integer menuSize;

	private String menuBg;

	private Integer buttonTag;

	private String button;

	private List<MenusDTO> sysMenuList;

	private List<String> permissionList;

	/**
	 * 不是SYS_MENU表中的字段，表示某个角色下的叶子菜单是否被选中
	 */
	private String checked;

	private static final long serialVersionUID = 1L;

	public SysMenu() {
	}

	public SysMenu(String no, String name) {
		this.no = no;
		this.name = name;
	}


}
