package com.zjft.microservice.treasurybrain.tauro.domain;

import com.zjft.microservice.treasurybrain.tauro.po.TauroTaskTablePO;
import lombok.Data;

@Data
public class TaskProcessDO extends TauroTaskTablePO {

	/**
	 * 操作人员1名称
	 */
	private String opName1;

	/**
	 * 操作人员2名称
	 */
	private String opName2;

	/**
	 * 加钞时间段--默认为全天--1、上午 2、下午 3、全天
	 */
	private Integer clrTimeInterval = 3;

	/**
	 * 加钞日期
	 */
	private String addnotesDate;

	/**
	 * 加钞编号
	 */
	private String addnotesPlanNo;

	/**
	 * 线路名称
	 */
	private String lineName;
}
