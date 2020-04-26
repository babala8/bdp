package com.zjft.microservice.treasurybrain.channelcenter.po;

import lombok.Data;

/**
 * @author 葛瑞莲
 * @since 2019/9/21
 */
@Data
public class OutsourcingPO {
	private String no;

	private String name;

	private Integer post;

	private Integer age;

	private String familyAddr;

	private String residenceAddr;

	private String mobile;

	//批量导入临时存储使用，无实际意义
	private int count;
}
