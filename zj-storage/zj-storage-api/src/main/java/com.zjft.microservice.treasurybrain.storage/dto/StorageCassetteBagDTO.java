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
@ApiModel(description = "钞袋库存信息",value = "钞袋库存信息")
public class StorageCassetteBagDTO extends DTO {


	@ApiModelProperty(value = "id唯一标识")
	private String id;

	@ApiModelProperty(value = "钞袋编号")
	private String cassetteBagNo;

	@ApiModelProperty(value = "钞箱列表")
	private List<StorageCassetteDTO> cassetteNoList;

	@ApiModelProperty(value = "库存金额")
	private BigDecimal amount;

}
