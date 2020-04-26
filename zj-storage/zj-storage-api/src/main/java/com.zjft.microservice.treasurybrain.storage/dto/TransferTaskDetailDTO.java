package com.zjft.microservice.treasurybrain.storage.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wupeng
 * @since 2019/8/28
 */
@Data
@ApiModel(value = "任务明细",description = "任务明细")
public class TransferTaskDetailDTO extends DTO {
	private String id;

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "申请金额")
	private BigDecimal applyAmount;

	@ApiModelProperty(value = "物品编号")
	private String entityNo;

	@ApiModelProperty(value = "容器类型")
	private String productNo;

	/*@ApiModelProperty(value = "物品类型",
	       example = "1-加钞钞袋  2-加钞钞箱  3-解现箱  4-寄存箱  5-钱捆  6-保管盒  7-现金实物")
	private int containerType;*/

	@ApiModelProperty(value = "出库日期")
	private String outDate;

	@ApiModelProperty(value = "寄库类型")
	private int depositType;

	@ApiModelProperty(value = "调拨方向",
	       example = "1-出库  2-入库")
	private int direction;

	private List<TransferCurrencyTypeDTO> transferCurrencyTypeDTOS;

}
