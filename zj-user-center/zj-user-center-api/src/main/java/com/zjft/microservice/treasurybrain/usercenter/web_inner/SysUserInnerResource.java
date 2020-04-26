package com.zjft.microservice.treasurybrain.usercenter.web_inner;

import com.zjft.microservice.treasurybrain.common.dto.UserDTO;

import java.util.List;

/**
 * @author 韩 通
 * @since 2020-02-24
 */
public interface SysUserInnerResource {

	//根据用户编号查询邮箱地址
	String getUserEmail(String userName);

	List<UserDTO> qryRolesEmail(String roles);



	//根据用户查询手机号码
	String getUserPhoneNumber(String userName);


	//根据角色查询用户及手机号码
	List<UserDTO> getUserAndPhoneNumber(String role);


	//查讯所有人手机号
	List<UserDTO> getAllUserInfo();

	String getNameByUserName(String userName);
}
