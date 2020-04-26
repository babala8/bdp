package com.zjft.microservice.treasurybrain.usercenter.service;


import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 * @since 2019-02-26
 */
public interface SysUserService {

	UserDTO getCurrentUserDetail();

	UserDTO getCurrentUserInfo();

	PageDTO<UserDTO> getUserListByPage(Map<String, Object> params);

	DTO addUser(UserDTO userDTO);

	DTO updateUser(UserDTO userDTO);

	DTO deleteByUsername(String username);

	DTO modPassword(UserDTO userDTO);

	DTO resetPassword(UserDTO userDTO);

	ListDTO<UserDTO> getUserListForAddnotes();

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
