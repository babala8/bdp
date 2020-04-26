package com.zjft.microservice.treasurybrain.productcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/09/03
 */
@Data
@ApiModel(value = "商品详细信息",description = "商品详细信息")
public class ProductDetailDTO extends DTO {

	@ApiModelProperty(value = "商品编号")
	private String productNo;

	@ApiModelProperty(value = "所属分类")
	private String productType;

	@ApiModelProperty(value = "商品名称")
	private String productName;

	@ApiModelProperty(value = "商品属性")
	private List<ProductPropertyKeyValueDTO> productPropertyKeyValueDTOList;

	public ProductDetailDTO() {
	}

	public ProductDetailDTO(RetCodeEnum re) {
		super(re);
	}
}
