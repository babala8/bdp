package com.zjft.microservice.treasurybrain.storage.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liuyuan
 * @since 2019/8/26 17:21
 */
@Data
@ApiModel(value = "库房物品")
public class StorageSortedEntityDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "金库编号",example = "028001")
	private String clrCenterNo;

	@ApiModelProperty(value = "金库名称",example = "工商银行成都分行金库")
	private String clrCenterName;

	@ApiModelProperty(value = "实物编号",example = "051003281998800001")
	private String entityNo;

	@ApiModelProperty(value = "实物类型",example = "R0003")
	private String productNo;

	@ApiModelProperty(value = "实物名称",example = "保管盒")
	private String productName;

	@ApiModelProperty(value = "物品类型",example = "1")
	private Integer entityType;

	@ApiModelProperty(value = "金额",example = "10000")
	private BigDecimal amount;

	@ApiModelProperty(value = "实物属性列表")
	private List<StorageEntityPropertyDTO> entityDetail;


	public StorageSortedEntityDTO() {
	}

	public StorageSortedEntityDTO(RetCodeEnum re) {
		super(re);
	}
}
