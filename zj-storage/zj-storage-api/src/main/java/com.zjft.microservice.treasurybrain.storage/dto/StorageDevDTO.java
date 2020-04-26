package com.zjft.microservice.treasurybrain.storage.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 葛瑞莲
 * @since 2019/8/14
 */
@Data
@ApiModel(description = "仓储物品设备信息",value = "仓储物品设备信息")
public class StorageDevDTO extends DTO {

	@ApiModelProperty(value = "设备编号")
	private String devNo;

	@ApiModelProperty(value = "钞袋列表")
	private List<StorageCassetteBagDTO> cassetteBagList;

	@ApiModelProperty(value = "库存金额")
	private BigDecimal amount;

}
