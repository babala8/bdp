package com.zjft.microservice.treasurybrain.storage.domain;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 葛瑞莲
 * @since 2019/8/14
 */
@Data
@ApiModel(description = "仓储物品流转信息",value = "仓储物品流转信息")
public class StorageEntityTransferDO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "任务单编号")
	private String taskNo;

	@ApiModelProperty(value = "绑定笼车编号")
	private String shelfNo;

	@ApiModelProperty(value = "线路编号")
	private String lineNo;

	@ApiModelProperty(value = "线路名称")
	private String lineName;

	@ApiModelProperty(name = "实物信息【容器编号列表】")
	private List<String> goods;

}
