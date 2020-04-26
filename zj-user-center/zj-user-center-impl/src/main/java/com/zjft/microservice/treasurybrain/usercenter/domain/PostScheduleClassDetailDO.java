package com.zjft.microservice.treasurybrain.usercenter.domain;

import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/26
 */
@Data
public class PostScheduleClassDetailDO {

	/*
	 *生成排班信息用
	 *
	 */

	private List<String> opList;

	private String classesNo;

}
