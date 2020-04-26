package com.zjft.microservice.treasurybrain.lock.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *	锁具状态
 * @author 韩通
 * @since 2019-06-26
 */
@Data
@ApiModel(value = "锁具状态", description = "锁具状态")
public class LockStatusTableDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "锁具序列号")
	private String lockCode;

	@ApiModelProperty(value = "监控状态")
	private String status;

	@ApiModelProperty(value = "更新时间")
	private String updateTime;
}
