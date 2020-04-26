package com.zjft.microservice.treasurybrain.storage.domain;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 葛瑞莲
 * @since 2019/8/14
 */
@Data
@ApiModel(description = "仓储物品详细信息",value = "仓储物品详细信息")
public class StorageEntityDetailDO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "笼车编号")
	private String shelfNo;

	@ApiModelProperty(value = "加钞线路")
	private String lineNo;

	@ApiModelProperty(value = "线路名称")
	private String lineName;

	@ApiModelProperty(value = "设备" )
	private StorageDevDO devs;

}
