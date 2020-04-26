package com.zjft.microservice.treasurybrain.storage.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/30 09:25
 */

@Data
@ApiModel(value = "库房流转物品")
public class StorageSortedTransferEntityDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "详情编号",example = "test")
	private String id;

	@ApiModelProperty(value = "流转记录编号",example = "test")
	private String recordNo;

	@ApiModelProperty(value = "容器编号",example = "051003281998800001")
	private String containerNo;

	@ApiModelProperty(value = "容器类型",example = "6")
	private Integer containerType;

	@ApiModelProperty(value = "容器类型名称",example = "保管盒")
	private String containerName;

	@ApiModelProperty(value = "物品类型",example = "1")
	private Integer entityType;

	@ApiModelProperty(value = "数量",example = "1")
	private Double amount;

	@ApiModelProperty(value = "物品现金列表")
	private List<StorageSortedEntityMoneyDTO> moneyInfos;

	public StorageSortedTransferEntityDTO() {
	}

	public StorageSortedTransferEntityDTO(RetCodeEnum re) {
		super(re);
	}

	@ApiModelProperty(value = "物品属性列表")
	private List<StorageEntityPropertyDTO> propertyInfos;

}
