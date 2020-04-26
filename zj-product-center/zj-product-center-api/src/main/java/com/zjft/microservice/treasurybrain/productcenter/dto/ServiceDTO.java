package com.zjft.microservice.treasurybrain.productcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "产品基础信息", description = "产品基础信息")
public class ServiceDTO extends DTO {

	@ApiModelProperty(value = "产品编号")
	Integer serviceNo;

	@ApiModelProperty(value = "产品名称")
	String serviceName;

	@ApiModelProperty(value = "服务对象no")
	Integer customerType;

	@ApiModelProperty(value = "服务对象名称")
	String customerName;

	@ApiModelProperty(value = "创建时间")
	String createTime;

	@ApiModelProperty(value = "更新时间")
	String updateTime;

	@ApiModelProperty(value = "产品描述")
	String note;

	@ApiModelProperty(value = "产品状态")
	Integer status;

	@ApiModelProperty(value = "产品类型")
	Integer type;

	public ServiceDTO() {
	}

	public ServiceDTO(RetCodeEnum re) {
		super(re);
	}
}
