package com.zjft.microservice.treasurybrain.accountscenter.domain;

import lombok.Data;

/**
 * @author 常 健
 * @since 2019/10/12
 */
@Data
public class BiztxlogDO {

	private String txType;
	private String cashType;
	private String txStatus;
	private String txDate;
	private String txInfo;
	private String mediumNo1;
	private String mediumNo2;

}
