package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 韩通
 * @since 2020-01-06
 */
@Data
@ApiModel(description = "钞袋库存信息",value = "钞袋库存信息")
public class StorageUseCassetteBagDTO extends DTO {

	@ApiModelProperty(value = "id唯一标识")
	private String id;

	@ApiModelProperty(value = "钞袋编号")
	private String cassetteBagNo;

//	@ApiModelProperty(value = "钞箱列表")
//	private List<StorageCassetteDO> cassetteNoList;

	@ApiModelProperty(value = "库存金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "币种")
	private String currencyCode;

	@ApiModelProperty(value = "面值")
	private Integer denomination;

	@ApiModelProperty(value = "钞币类别")
	private Integer currencyType;

}
