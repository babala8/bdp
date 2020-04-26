package com.zjft.microservice.treasurybrain.lock.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *	锁具交易记录
 * @author 韩通
 * @since 2019-06-26
 */
@Data
@ApiModel(value = "锁具交易记录", description = "锁具交易记录")
public class LockTransTableDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "交易流水号")
	private String serialNo;

	@ApiModelProperty(value = "设备编号")
	private String devNo;

	@ApiModelProperty(value = "锁具序列号")
	private String lockCode;

	@ApiModelProperty(value = "交易日期")
	private String tranDate;

	@ApiModelProperty(value = "交易时间")
	private String tranTime;

	@ApiModelProperty(value = "交易类型")
	private String tranType;

	@ApiModelProperty(value = "密码服务器返回码")
	private String encryptCode;

	@ApiModelProperty(value = "ESB服务器返回码")
	private String esbCode;

	@ApiModelProperty(value = "返回码")
	private String retCode;

	@ApiModelProperty(value = "返回信息")
	private String retMsg;


	@ApiModelProperty(value = "交易开始时间")
	private String tranStartDate;

	@ApiModelProperty(value = "交易结束时间")
	private String tranEndDate;
}
