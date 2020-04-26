package com.zjft.microservice.treasurybrain.lock.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 锁具基本信息
 *
 * @author 韩通
 * @since 2019-06-26
 */
@Data
@ApiModel(value = "锁具基本信息", description = "锁具基本信息")
public class LockBaseInfoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "锁具序列号")
	private String lockCode;

	@ApiModelProperty(value = "设备编号")
	private String devNo;

	@ApiModelProperty(value = "锁具版本")
	private String version;

	@ApiModelProperty(value = "C端程序版本")
	private String cversion;

	@ApiModelProperty(value = "是否激活")
	private Integer state;

	@ApiModelProperty(value = "生产日期")
	private String madeDate;

	@ApiModelProperty(value = "安装日期")
	private String installDate;

	@ApiModelProperty(value = "备注")
	private String note;

	@ApiModelProperty(value = "闭锁码")
	private String blockNum;

	public LockBaseInfoDTO() {

	}

	public LockBaseInfoDTO(RetCodeEnum re) {
		super(re);
	}
}
