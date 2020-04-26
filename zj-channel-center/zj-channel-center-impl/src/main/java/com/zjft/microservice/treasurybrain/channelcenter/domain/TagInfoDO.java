package com.zjft.microservice.treasurybrain.channelcenter.domain;

import com.zjft.microservice.treasurybrain.channelcenter.po.TagInfoPO;
import lombok.Data;

/**
 * @author 葛瑞莲
 * @since 2019/6/17
 */
@Data
public class TagInfoDO extends TagInfoPO {

	/**
	 * 清机中心名称（金库名称）
	 */
	private String centerName;

}
