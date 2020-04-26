package com.zjft.microservice.treasurybrain.productcenter.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "产品信息详情", description = "产品信息详情")
public class ServiceDetailDTO extends ServiceDTO {

	@ApiModelProperty(value = "状态列表")
	List<ServiceStatusDTO> statusList;

	@ApiModelProperty(value = "流程转换关系表")
	List<ServiceStatusConvertDTO> statusConvertList;

	@ApiModelProperty(value = "物品信息")
	List<ServiceProductDTO> productList;

	@ApiModelProperty(value = "客户信息")
	List<CallCustomerDTO> customerList;

	public ServiceDetailDTO() {
	}

	@ApiModelProperty(value = "参数信息")
	List<SelfServiceProductArgumentDTO> selfServiceProductArgumentDTOList;

	public ServiceDetailDTO(RetCodeEnum re) {
		super(re);
	}

}
