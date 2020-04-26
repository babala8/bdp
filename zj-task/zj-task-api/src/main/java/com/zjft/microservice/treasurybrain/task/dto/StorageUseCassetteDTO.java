package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 韩通
 * @since 2020-01-06
 */
@Data
@ApiModel(description = "钞箱库存信息",value = "钞箱库存信息")
public class StorageUseCassetteDTO extends DTO {

	@ApiModelProperty(value = "id唯一标识")
	private String id;

	@ApiModelProperty(value = "钞箱编号")
	private String cassetteNo;

	@ApiModelProperty(value = "库存金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "钞箱详细信息")
	private List<StorageUseCassetteBagDetailDTO>  cassetteBagDetailList;

}
