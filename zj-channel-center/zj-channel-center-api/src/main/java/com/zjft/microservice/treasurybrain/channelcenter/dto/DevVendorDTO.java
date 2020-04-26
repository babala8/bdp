package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DevVendorDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String no;

	private String name;

	private String address;

	private String hotLine1;

	private String status;

}