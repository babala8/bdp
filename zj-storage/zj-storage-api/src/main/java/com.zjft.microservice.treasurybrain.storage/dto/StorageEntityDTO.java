package com.zjft.microservice.treasurybrain.storage.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liuyuan
 * @since 2019/8/13 15:09
 */
@Data
@ApiModel(description = "仓储物品信息",value = "仓储物品信息")
public class StorageEntityDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "所属金库编号",example = "028001")
	private String clrCenterNo;

	@ApiModelProperty(value = "所属金库名称")
	private String clrCenterName;

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "笼车编号")
	private String shelfNo;

	@ApiModelProperty(value = "加钞时间")
	private String addnotesDate;

	@ApiModelProperty(value = "加钞线路")
	private String lineNo;

	@ApiModelProperty(value = "线路名称")
	private String lineName;

	@ApiModelProperty(value = "库存金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "入库时间")
	private String inTime;

	@ApiModelProperty(value = "任务单状态" )
	private String taskStatus;

	@ApiModelProperty(value = "任务单状态描述" )
	private String statusDesc;

}
