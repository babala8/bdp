package com.zjft.microservice.treasurybrain.storage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/10/10
 */
@Data
public class CheckInventoryDTO {

	@ApiModelProperty(value = "金库编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "现金详情")
	private List<CheckInventoryDetailDTO> checkInventoryDetailDTOList;
}
