package com.zjft.microservice.treasurybrain.common.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "角色", description = "角色")
public class RoleDTO extends DTO {

	private Integer no;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private Integer catalog;

	/**
	 * 机构级别
	 */
	private Integer orgGradeNo;

	/**
	 * 描述
	 */
	private String note;

	private List<MenuDTO> menuList;

	public RoleDTO() {
	}

	public RoleDTO(RetCodeEnum re) {
		super(re);
	}

}
