package com.zjft.microservice.treasurybrain.common.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 杨光
 */
@Getter
@Setter
@ToString
@ApiModel(value = "机构等级", description = "机构等级")
public class SysOrgGradeDTO extends DTO {

	@ApiModelProperty(value = "机构等级编号")
	private Integer no;

	@ApiModelProperty(value = "机构等级名称")
	private String name;

	@ApiModelProperty(value = "机构类型")
	private String orgType;

	public SysOrgGradeDTO() {
	}

	public SysOrgGradeDTO(RetCodeEnum re) {
		super(re);
	}

}
