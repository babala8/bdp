package com.zjft.microservice.treasurybrain.usercenter.service.impl;


import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrglnnerResource2;
import com.zjft.microservice.treasurybrain.common.domain.SysRoleDO;
import com.zjft.microservice.treasurybrain.common.domain.SysUserDO;
import com.zjft.microservice.treasurybrain.common.domain.SysUserPostDO;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.mapstruct.ClrCenterConverter;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysRoleConverter;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysUserConverter;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.SysUserPostConverter;
import com.zjft.microservice.treasurybrain.usercenter.repository.SysUserMapper;
import com.zjft.microservice.treasurybrain.usercenter.service.SysUserService;
import com.zjft.microservice.treasurybrain.usercenter.util.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author 杨光
 * @since 2019-03-08
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

	private final SysUserMapper sysUserMapper;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public SysUserServiceImpl(SysUserMapper sysUserMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.sysUserMapper = sysUserMapper;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Resource
	private SysOrglnnerResource2 sysOrglnnerResource2;

	/**
	 * 查询当前登录用户详情：用户详情包括（用户机构，用户的角色列表，用户的菜单列表）
	 *
	 * @return UserDTO
	 */
	@Override
	public UserDTO getCurrentUserDetail() {
		String username = ServletRequestUtil.getUsername();
		SysUserDO sysUserDO = sysUserMapper.selectByPrimaryKey(username);
		UserDTO dto = SysUserConverter.INSTANCE.domain2dto(sysUserDO);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	 * 查询当前登录的用户信息
	 *
	 * @return UserDTO
	 */
	@Override
	public UserDTO getCurrentUserInfo() {
		String username = ServletRequestUtil.getUsername();
		SysUserDO sysUserDO = sysUserMapper.selectInfoByPrimaryKey(username);
		UserDTO dto = SysUserConverter.INSTANCE.domain2dto(sysUserDO);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	 * 分页查询用户列表
	 * 1.获取当前用户信息，userOrgNo
	 * 2.查询totalRow
	 * 3.根据当前用户机构，查询其权限范围内的用户
	 *
	 * @param map orgNo roleNo username name curPage pageSize
	 * @return PageDTO<UserDTO>
	 */
	@Override
	public PageDTO<UserDTO> getUserListByPage(Map<String, Object> map) {
		PageDTO<UserDTO> dto = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(map, dto);

		UserDTO authUserDTO = getCurrentUserInfo();
		map.put("userOrgNo", authUserDTO.getOrgNo());

		int totalRow = sysUserMapper.queryTotalRow(map);
		List<SysUserDO> userListDO = sysUserMapper.queryByPage(map);
		List<UserDTO> dtoList = SysUserConverter.INSTANCE.domain2dto(userListDO);

		dto.setTotalPage(PageUtil.computeTotalPage(pageSize, totalRow));
		dto.setTotalRow(totalRow);
		dto.setRetList(dtoList);
		return dto;
	}

	/**
	 * 添加用户
	 * 1.检查权限
	 * 2.前端发来的密码使用md5加密，后端使用BCrypt进行强哈希加密
	 * 3.添加用户角色
	 *
	 * @param dto 必要的用户信息
	 * @return DTO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO addUser(UserDTO dto) {
		if (!checkPermissionByOrgNo(dto.getOrgNo())) {
			return new DTO(RetCodeEnum.FAIL);
		}

		SysUserDO domain = new SysUserDO();
		domain.setUsername(dto.getUsername());
		domain.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()).replace("{bcrypt}", ""));
		domain.setName(dto.getName());
		domain.setOrgNo(dto.getOrgNo());
		domain.setPhone(dto.getPhone());
		domain.setMobile(dto.getMobile());
		domain.setEmail(dto.getEmail());
		domain.setPhoto(dto.getPhoto());
		domain.setGroupType(dto.getGroupType());
		domain.setServiceCompany(dto.getServiceCompany());

		if (sysUserMapper.insert(domain) == 1) {
			List<RoleDTO> roleList = dto.getRoleList();
			List<SysUserPostDTO> postList = dto.getPostList();
			List<SysRoleDO> doList = SysRoleConverter.INSTANCE.dto2do(roleList);
			List<SysUserPostDO> sysUserPostDOList = SysUserPostConverter.INSTANCE.dto2do(postList);
			if (roleList.size() != 0) {
				sysUserMapper.insertUserRole(dto.getUsername(), doList);
			}
			//添加用户-岗位对应关系表
			if (postList.size() != 0) {
				sysUserMapper.insertUserPost(dto.getUsername(), sysUserPostDOList);
			}
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 更新用户
	 * 1.检查权限
	 * 2.更新用户信息
	 * 3.更新用户的角色信息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO updateUser(UserDTO dto) {
		if (!checkPermissionByOrgNo(dto.getOrgNo())) {
			return new DTO(RetCodeEnum.FAIL);
		}

		SysUserDO domain = SysUserConverter.INSTANCE.dto2do(dto);
		List<RoleDTO> roleList = dto.getRoleList();
		List<SysUserPostDTO> postList = dto.getPostList();
		sysUserMapper.updateByPrimaryKey(domain);


		List<SysRoleDO> domainList = SysRoleConverter.INSTANCE.dto2do(roleList);
		sysUserMapper.deleteUserRole(dto.getUsername());
		sysUserMapper.insertUserRole(dto.getUsername(), domainList);

		List<SysUserPostDO> sysUserPostDOList = SysUserPostConverter.INSTANCE.dto2do(postList);
		sysUserMapper.deleteUserPost(dto.getUsername());
		sysUserMapper.insertUserPost(dto.getUsername(), sysUserPostDOList);

		return new DTO(RetCodeEnum.SUCCEED);
	}

	/**
	 * 根据用户名删除用户
	 * 1.检查当前登录用户权限
	 * 2.删除用户关联信息：角色
	 * 3.删除用户
	 *
	 * @param username 用户名
	 * @return DTO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO deleteByUsername(String username) {
		if (!checkPermissionByUsername(username)) {
			return new DTO(RetCodeEnum.FAIL);
		}

		sysUserMapper.deleteUserRole(username);
		sysUserMapper.deleteUserPost(username);
		if (sysUserMapper.deleteByPrimaryKey(username) == 1) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 用户修改密码
	 * 前端发来的密码使用md5加密，后端使用BCrypt进行强哈希加密
	 *
	 * @param userDTO 用户旧密码：password  用户新密码：newPassword
	 * @return DTO
	 */
	@Override
	public DTO modPassword(UserDTO userDTO) {
		SysUserDO authUserDO = sysUserMapper.selectInfoByPrimaryKey(ServletRequestUtil.getUsername());
		String username = authUserDO.getUsername();
		String password = userDTO.getPassword();
		if (bCryptPasswordEncoder.matches(password, authUserDO.getPassword())) {

			String newPassword = bCryptPasswordEncoder.encode(userDTO.getNewPassword()).replace("{bcrypt}", "");
			if (sysUserMapper.modPassword(username, newPassword) == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 管理员重置密码
	 * 1.检查当前登录用户的权限
	 * 2.前端将用户名进行MD5加密，作为用户的重置密码
	 * 3.使用BCrypt对newPassword进行强哈希加密
	 *
	 * @param userDTO 重置的用户名：username  前端发来的重置密码：newPassword
	 * @return DTO
	 */
	@Override
	public DTO resetPassword(UserDTO userDTO) {
		String username = userDTO.getUsername();

		if (checkPermissionByUsername(username)) {
			String newPassword = bCryptPasswordEncoder.encode(userDTO.getNewPassword()).replace("{bcrypt}", "");
			if (sysUserMapper.modPassword(username, newPassword) == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}
		return new DTO(RetCodeEnum.FAIL);
	}


	/**
	 * 根据机构号检查用户权限：当前用户是否是此机构用户，或者是更高级的机构用户
	 */
	private boolean checkPermissionByOrgNo(String orgNo) {
		UserDTO user = getCurrentUserInfo();
		return sysOrglnnerResource2.checkPermissionByOrgNo(orgNo, user.getOrgNo()) > 0;
	}

	/**
	 * 根据用户名检查用户权限：当前用户是否跟目标用户在同一机构，或者比目标用户更高一级
	 */
	private boolean checkPermissionByUsername(String username) {
		UserDTO user = getCurrentUserInfo();
		return sysOrglnnerResource2.checkPermissionByUsername(username, user.getOrgNo()) > 0;
	}

	@Override
	public ListDTO<UserDTO> getUserListForAddnotes() {
		log.info("----------[getUserListForAddnotes]SysUserServiceImpl----------------");
		ListDTO<UserDTO> listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		UserDTO authUserDto = getCurrentUserInfo();
		String userOrgNo = authUserDto.getOrgNo();
		List<SysUserDO> sysUserDOList = sysUserMapper.getUserListForAddnotes(userOrgNo);
		List<UserDTO> userDTOList = ClrCenterConverter.INSTANCE.domain2dto1(sysUserDOList);
		listDTO.setRetList(userDTOList);
		listDTO.setResult(RetCodeEnum.SUCCEED);
		return listDTO;
	}

	@Override
	public String getUserEmail(String userName) {
		return sysUserMapper.getUserEmail(userName);
	}

	@Override
	public List<UserDTO> qryRolesEmail(String roles) {

		String[] split = roles.split(",");
		List<String> roleList = new ArrayList<>();
		for (String roleNo : split) {
			if (!roleList.contains(roleNo)) {
				roleList.add(roleNo);
			}
		}
		List<SysUserDO> sysUserDO = sysUserMapper.qryRolesEmail(roleList);
		return SysUserConverter.INSTANCE.domain2dto(sysUserDO);
	}

	@Override
	public String getUserPhoneNumber(String userName) {
		return sysUserMapper.getUserPhoneNumber(userName);
	}

	@Override
	public List<UserDTO> getUserAndPhoneNumber(String role) {
		String[] split = role.split(",");
		List<String> roleList = new ArrayList<>();
		for (String roleNo : split) {
			if (!roleList.contains(roleNo)) {
				roleList.add(roleNo);
			}
		}
		List<UserDTO> dtoList = new ArrayList<>();
		List<SysUserDO> sysUserDO = sysUserMapper.getUserAndPhoneNumber(roleList);
		List<UserDTO> userDTOList = SysUserConverter.INSTANCE.domain2dto(sysUserDO);
		dtoList.addAll(userDTOList);
		return dtoList;
	}

	@Override
	public List<UserDTO> getAllUserInfo() {
		return SysUserConverter.INSTANCE.domain2dto(sysUserMapper.getAllUserInfo());
	}

	@Override
	public String getNameByUserName(String userName) {
		return sysUserMapper.getNameByUserName(userName);
	}

}
