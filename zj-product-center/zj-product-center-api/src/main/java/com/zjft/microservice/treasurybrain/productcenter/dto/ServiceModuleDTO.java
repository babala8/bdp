package com.zjft.microservice.treasurybrain.productcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "产品模块信息", description = "产品模块信息")
public class ServiceModuleDTO extends DTO {

	@ApiModelProperty(value = "模块编号")
	Integer moduleNo;

	@ApiModelProperty(value = "模块名称")
	String moduleName;

}
