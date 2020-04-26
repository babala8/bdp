package com.zjft.microservice.treasurybrain.usercenter.web;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户管理
 *
 * @author 杨光
 * @since 2019-03-08
 */
@Slf4j
@RestController
public class SysUserResourceImpl implements SysUserResource {

	private SysUserService sysUserService;

	@Autowired
	SysUserResourceImpl(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Override
	public DTO addUser(UserDTO user) {
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) ||
				StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getOrgNo())) {
			return new UserDTO(RetCodeEnum.PARAM_LACK);
		}
		try {
			return sysUserService.addUser(user);
		} catch (Exception e) {
			log.error("[添加用户信息异常]: ", e);
			return new UserDTO(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 修改用户信息
	 */
	@Override
	public DTO updateUserByNo(UserDTO u) {
		if (StringUtil.isBlank(u.getUsername())
				|| StringUtils.isBlank(u.getName())
				|| StringUtils.isBlank(u.getOrgNo())) {
			return new UserDTO(RetCodeEnum.PARAM_LACK);
		}
		try {
			return sysUserService.updateUser(u);

		} catch (Exception e) {
			log.error("[修改用户信息异常]: ", e);
			return new UserDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO deleteUser(String username) {
		log.info("[删除用户]: {}", username);
		if (StringUtils.isBlank(username)) {
			return new UserDTO(RetCodeEnum.PARAM_LACK);
		}
		try {
			return sysUserService.deleteByUsername(username);
		} catch (Exception e) {
			log.error("[删除用户信息异常]: ", e);
			return new UserDTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public PageDTO getUserListByPage(Map<String, Object> params) {
		try {
			return sysUserService.getUserListByPage(params);
		} catch (Exception e) {
			log.error("[获取分页用户列表异常]: ", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 登录后，查询当前登录用户详情，用户详情包括（用户机构，用户角色列表，用户菜单列表）
	 *
	 * @return UserDTO
	 */
	@Override
	public UserDTO getAuthUserDetail() {
		try {
			return sysUserService.getCurrentUserDetail();
		} catch (Exception e) {
			log.error("[获取用户详情异常]: ", e);
			return new UserDTO(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 获取当前登录的用户信息
	 */
	@Override
	public UserDTO getAuthUserInfo() {
		try {
			return sysUserService.getCurrentUserInfo();
		} catch (Exception e) {
			log.error("[获取用户信息异常]: ", e);
			return new UserDTO(RetCodeEnum.FAIL);
		}
	}

	/**
	 * 用户修改密码
	 *
	 * @param dto password newPassword
	 * @return DTO
	 */
	@Override
	public DTO modPassword(UserDTO dto) {
		if (StringUtil.isNullorEmpty(dto.getPassword()) || StringUtil.isNullorEmpty(dto.getNewPassword())) {
			return new UserDTO(RetCodeEnum.FAIL);
		}
		try {
			return sysUserService.modPassword(dto);
		} catch (Exception e) {
			log.error("[修改密码异常]: ", e);
			return new DTO(RetCodeEnum.FAIL);
		}
	}


	/**
	 * 管理员重置密码
	 *
	 * @param dto username newPassword
	 * @return DTO
	 */
	@Override
	public DTO resetPassword(UserDTO dto) {
		if (StringUtil.isNullorEmpty(dto.getUsername()) || StringUtil.isNullorEmpty(dto.getNewPassword())) {
			return new UserDTO(RetCodeEnum.FAIL);
		}
		try {
			return sysUserService.resetPassword(dto);
		} catch (Exception e) {
			log.error("[重置密码异常]: ", e);
			return new DTO(RetCodeEnum.FAIL);
		}
	}

	@Override
	public ListDTO<UserDTO> getUserListForAddnotes() {
		log.info("----------[SysUserResourceImpl]getUserListForAddnotes----------------");
		try {
			return sysUserService.getUserListForAddnotes();
		} catch (Exception e) {
			log.error("[查询加钞人员用户列表异常]: ", e);
			return new ListDTO<>(RetCodeEnum.FAIL);
		}
	}

}
