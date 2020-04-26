package com.zjft.microservice.treasurybrain.clearcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/7 14:58
 */
@Data
@ApiModel(value = "容器配钞信息",description = "容器配钞信息")
public class ContainerEntityDTO extends DTO {

	@ApiModelProperty(value = "容器编号")
	private String entityNo;

	@ApiModelProperty(value = "容器类型" )
	private String productNo;

	@ApiModelProperty(value = "物品类型:1-现金 2-贵金属 3-重空" )
	private Integer entityType;

	@ApiModelProperty(value = "调运方向")
	private Integer direction;

	@ApiModelProperty(value = "上级容器编号")
	private String parentEntity;

	@ApiModelProperty(value = "是否为最下级容器" , example = "0-不是 1-是")
	private Integer leafFlag;

	@ApiModelProperty(value = "寄库类型：1、长寄库，2、短寄库")
	private Integer depositType;

	@ApiModelProperty(value = "容器详情")
	private List<ContainerEntityDetailDTO> containerEntityDetailDTOList;

	@ApiModelProperty(value = "金额")
	private double amount;
}
