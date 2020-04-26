package com.zjft.microservice.treasurybrain.business.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 韩 通
 * @since 2020-02-25
 */
@Data
@ApiModel(value = "订单产品信息" ,description = "订单产品信息")
public class TaskProductDTO extends DTO {

	@ApiModelProperty(value = "订单编号")
	private String taskNo;

	@ApiModelProperty(value = "产品编号")
	private String productNo;

	@ApiModelProperty(value = "调拨方向")
	private String direction;

	@ApiModelProperty(value = "金额/重量/数量")
	private Integer amount;

	@ApiModelProperty(value = "属性")
	private List<PropertyDTO> detailList;

}
