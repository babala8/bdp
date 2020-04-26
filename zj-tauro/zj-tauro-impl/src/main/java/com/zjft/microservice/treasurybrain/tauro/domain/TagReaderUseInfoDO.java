package com.zjft.microservice.treasurybrain.tauro.domain;

import com.zjft.microservice.treasurybrain.tauro.po.TagReaderUseInfoPO;
import lombok.Data;

@Data
public class TagReaderUseInfoDO extends TagReaderUseInfoPO {

	/**
	 * 申请人员名称
	 */
	private String requestOpName;

	/**
	 * 审核（发放）人员名称
	 */
	private String grantOpName;

	/**
	 * 签收人员名称
	 */
	private String signOpName;

	/**
	 * 归还人员名称"
	 */
	private String returnOpName;
}
