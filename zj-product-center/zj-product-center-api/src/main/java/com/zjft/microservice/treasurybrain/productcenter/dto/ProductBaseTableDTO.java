package com.zjft.microservice.treasurybrain.productcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 常 健
 * @since 2019/09/02
 */
@Data
@ApiModel(value = "商品信息",description = "商品信息")
public class ProductBaseTableDTO extends DTO {

	@ApiModelProperty(value = "商品编号")
	private String productNo;

	@ApiModelProperty(value = "父级商品编号")
	private String productType;

	@ApiModelProperty(value = "商品名称")
	private String productName;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@ApiModelProperty(value = "更新时间")
	private String updateTime;

}
