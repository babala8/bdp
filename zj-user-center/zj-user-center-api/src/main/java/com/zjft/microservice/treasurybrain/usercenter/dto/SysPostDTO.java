package com.zjft.microservice.treasurybrain.usercenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/11
 */
@Data
@ApiModel(value = "岗位信息-添加制约信息", description = "岗位信息-添加制约信息")
public class SysPostDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "岗位编号", example = "000001")
	private String postNo;

	@ApiModelProperty(value = "岗位名称", example = "调运岗")
	private String postName;

	@ApiModelProperty(value = "岗位类别", example = "1")
	private Short postType;

	@ApiModelProperty(value = "注释", example = "注释")
	private String note;

	@ApiModelProperty(value = "制约岗位信息")
	private List<SysPostLimitDTO> postLimitList;

}
