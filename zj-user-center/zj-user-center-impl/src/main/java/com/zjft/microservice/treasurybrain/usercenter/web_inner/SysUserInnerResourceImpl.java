package com.zjft.microservice.treasurybrain.usercenter.web_inner;

import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.usercenter.service.SysUserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 韩 通
 * @since 2020-02-24
 */
@RestController
public class SysUserInnerResourceImpl implements SysUserInnerResource {

	@Resource
	private SysUserService sysUserService;
	@Override
	public String getUserEmail(String userName) {
		return sysUserService.getUserEmail(userName);
	}

	@Override
	public List<UserDTO> qryRolesEmail(String roles) {
		return sysUserService.qryRolesEmail(roles);
	}

	@Override
	public String getUserPhoneNumber(String userName) {
		return sysUserService.getUserPhoneNumber(userName);
	}

	@Override
	public List<UserDTO> getUserAndPhoneNumber(String role) {
		return sysUserService.getUserAndPhoneNumber(role);
	}

	@Override
	public List<UserDTO> getAllUserInfo() {
		return sysUserService.getAllUserInfo();
	}

	@Override
	public String getNameByUserName(String userName) {
		return sysUserService.getNameByUserName(userName);
	}
}
