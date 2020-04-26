package com.zjft.microservice.treasurybrain.productcenter.dto;

import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "产品信息详情", description = "产品信息详情")
public class SelfServiceDetailDTO extends SelfServiceDTO {

	@ApiModelProperty(value = "物品信息")
	List<SelfServiceProductDTO> productList;

	@ApiModelProperty(value = "客户信息")
	List<CallCustomerDTO> customerList;

	public SelfServiceDetailDTO() {
	}

	public SelfServiceDetailDTO(RetCodeEnum re) {
		super(re);
	}

}
