package com.zjft.microservice.treasurybrain.datainsight.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GisPointInfoDTO extends DTO {

private static final long serialVersionUID = 1L;

	private String orgNo;
	private String status;
	private String operator;
	private String htmlTemplate;
	private String jsTemplate;

	public GisPointInfoDTO(String retCode, String retMsg) {
		super(retCode, retMsg);
	}
	
	public GisPointInfoDTO() {
	}
	public GisPointInfoDTO(RetCodeEnum e) {
		super(e);
	}
	
}
