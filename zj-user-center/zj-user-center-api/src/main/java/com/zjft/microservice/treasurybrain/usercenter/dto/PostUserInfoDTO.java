package com.zjft.microservice.treasurybrain.usercenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/11/20
 */
@Data
@ApiModel(value = "岗位人员信息", description = "岗位人员信息")
public class PostUserInfoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名", example = "zhangsan")
	private String userName;

	@ApiModelProperty(value = "用户姓名", example = "张三")
	private String name;

	@ApiModelProperty(value = "机构编号", example = "1001")
	private String orgNo;

	@ApiModelProperty(value = "机构名称", example = "总行")
	private String orgName;
}
