package com.zjft.microservice.treasurybrain.usercenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 张弛
 * @since 2020-1-20
 *
 */
@Data
public class MenusDTO extends DTO {
	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "菜单编号")
	String no;

	@ApiModelProperty(value = "菜单名称")
	String name;
}
