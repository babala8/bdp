package com.zjft.microservice.treasurybrain.usercenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/15
 */
@Data
@ApiModel(value = "排班模板信息", description = "排班模板信息")
public class PostScheduleMouldDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "模板编号", example = "000001")
	private String mouldNo;

	@ApiModelProperty(value = "模板类型 1：周 2：月", example = "1")
	private Integer mouldType;

	@ApiModelProperty(value = "模板名称", example = "XX模板")
	private String mouldName;

	@ApiModelProperty(value = "所属机构编号", example = "1001")
	private String orgNo;

	@ApiModelProperty(value = "所属机构名称", example = "总行")
	private String orgName;

	@ApiModelProperty(value = "岗位编号", example = "000001")
	private String postNo;

	@ApiModelProperty(value = "岗位名称", example = "XX岗")
	private String postName;

	@ApiModelProperty(value = "创建时间", example = "2019-01-01")
	private String createTime;

	@ApiModelProperty(value = "模板详细信息")
	private List<PostScheduleMouldDetailDTO> detailList;
}
