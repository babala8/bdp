package com.zjft.microservice.treasurybrain.tauro.domain;

import com.zjft.microservice.treasurybrain.tauro.po.TagReaderCoordsInfoPO;
import lombok.Data;

@Data
public class TagReaderCoordsInfoDO extends TagReaderCoordsInfoPO {
	/**
	 * 使用人名称
	 */
	private String userName;
}
