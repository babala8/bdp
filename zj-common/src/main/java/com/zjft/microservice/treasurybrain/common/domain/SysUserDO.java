package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 杨光
 */
@Slf4j
@Data
public class SysUserDO {

	private String username;

	private String password;

	private String name;

	private Integer status;

	private Integer onlineFlag;

	private String orgNo;

	private String phone;

	private String mobile;

	private String email;

	private String photo;

	private String loginIp;

	private String loginTime;

	private String loginTerm;

	private String passwordExpiration;

	private Integer passwordError;

	private Integer groupType;

	private String serviceCompany;

	private SysOrg sysOrg;

	private List<SysRoleDO> roleList;

	private List<SysUserPostDO> postList;

	private List<SysPostDO> postDetailList;

	private List<SysMenuDO> menuList;


	public SysUserDO() {
	}


	public SysUserDO(SysUserDO user, List<SysRoleDO> roles) {
		this.username = user.username;
		this.password = user.password;
		this.roleList = roles;
	}

}
