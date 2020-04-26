package com.zjft.microservice.treasurybrain.usercenter.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/14
 */
@Data
@ApiModel(value = "岗位详情信息（详情用）", description = "岗位详情信息（详情用）")
public class SysPostDetailDTO extends SysPostDTO {

	private List<SysPostLimitDTO> postLimitList;

	public SysPostDetailDTO(){

	}

	public SysPostDetailDTO(RetCodeEnum re){
		super();
	}
}
