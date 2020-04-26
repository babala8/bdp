package com.zjft.microservice.treasurybrain.task.dto;

import lombok.Data;

/**
 * @author 韩通
 * @since 2020-01-07
 */

@Data
public class TaskNodeVariateDTO {

	/**
	 * 任务节点编号
	 */
	String taskNodeNo;
	/**
	 * 变量名
	 */
	String name;
	/**
	 * 变量类型
	 */
	String varType;
	/**
	 * 字符串型变量值
	 */
	String value;

}
