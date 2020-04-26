package com.zjft.microservice.treasurybrain.managecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/10
 */
@Data
@ApiModel(value = "款箱信息(用于添加，修改接口的参数)", description = "款箱信息(用于添加，修改接口的参数)")
public class BaseEntityDTO extends DTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "物品编号", example = "01001")
	private String entityNo;

	@ApiModelProperty(value = "物品类型编号", example = "X0001")
	private String goodsNo;

	@ApiModelProperty(value = "所属对象编号", example = "1001")
	private String customerNo;

	@ApiModelProperty(value = "所属对象类型", example = "1")
	private Integer customerType;

	@ApiModelProperty(value = "物品属性列表")
	private Map goodsPropertyList;
}
