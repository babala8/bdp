package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ApiModel(value = "服务商", description = "服务商")
public class DevServiceCompanyDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "编号")
	private String no;

	@ApiModelProperty(value = "服务商名称")
	private String name;

	@ApiModelProperty(value = "联系人")
	private String linkman;

	@ApiModelProperty(value = "地址")
	private String address;

	@ApiModelProperty(value = "固定电话")
	private String phone;

	@ApiModelProperty(value = "手机")
	private String mobile;

	@ApiModelProperty(value = "传真")
	private String fax;

	@ApiModelProperty(value = "电子邮箱")
	private String email;

	@ApiModelProperty(value = "服务商类型")
	private Integer type;

	public DevServiceCompanyDTO() {

	}

	public DevServiceCompanyDTO(RetCodeEnum e) {
		super(e);
	}
}
