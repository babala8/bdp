package com.zjft.microservice.treasurybrain.usercenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.SysPostDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/13
 */
@Data
@ApiModel(value = "用户信息（添加岗位详情）", description = "用户信息（添加岗位详情）")
public class SysUserPostDetailDTO extends UserDTO {

	@ApiModelProperty(value = "岗位信息")
	private List<SysPostDTO> userPostDetailList;
}
