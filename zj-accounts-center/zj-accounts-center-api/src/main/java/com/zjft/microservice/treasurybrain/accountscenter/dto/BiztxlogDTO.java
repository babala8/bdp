package com.zjft.microservice.treasurybrain.accountscenter.dto;

import lombok.Data;

/**
 * @author 常 健
 * @since 2019/10/10
 */
@Data
public class BiztxlogDTO {

	private String txType;
	private String cashType;
	private String txStatus;
	private String txDate;
	private String txInfo;
	private String mediumNo1;
	private String mediumNo2;

}
