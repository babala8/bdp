package com.zjft.microservice.treasurybrain.common.dto;

import com.zjft.microservice.treasurybrain.common.domain.SysPostDO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 杨光
 */
@Getter
@Setter
@ToString
@ApiModel(value = "用户", description = "用户")
public class UserDTO extends DTO {
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String newPassword;

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

	private List<RoleDTO> roleList;

	@ApiModelProperty(value = "用户-岗位", example = "用户-岗位")
	private List<SysUserPostDTO> postList;

	@ApiModelProperty(value = "岗位信息", example = "岗位信息")
	private List<SysPostDO> postDetailList;

	private SysOrgDTO sysOrg;

	private List<MenuDTO> menuList;


	public UserDTO() {
	}

	public UserDTO(RetCodeEnum re) {
		super(re);
	}

}
