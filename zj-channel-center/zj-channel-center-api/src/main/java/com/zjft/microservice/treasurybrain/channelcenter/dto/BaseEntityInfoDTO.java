package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/10
 */
@Data
@ApiModel(value = "款箱基础信息", description = "款箱信息")
public class BaseEntityInfoDTO extends DTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "物品编号", example = "01001")
	private String entityNo;

	@ApiModelProperty(value = "物品类型编号", example = "X0001")
	private String goodsNo;

	@ApiModelProperty(value = "物品类型名称", example = "人民币")
	private String goodsName;

	@ApiModelProperty(value = "属性json字符串", example = "{\"券别\":[\"100\",\"50\"]}")
	private String prams;

	@ApiModelProperty(value = "属性列表")
	private Map pramsList;

	@ApiModelProperty(value = "所属对象编号", example = "1001")
	private String customerNo;

	@ApiModelProperty(value = "所属对象名称", example = "工总行")
	private String customerName;

	@ApiModelProperty(value = "所属对象类型", example = "1")
	private String customerType;

	@ApiModelProperty(value = "所属对象类型名称", example = "人民银行")
	private String customerTypeName;

	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@ApiModelProperty(value = "最后修改时间")
	private String modTime;

}
