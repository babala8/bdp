package com.zjft.microservice.treasurybrain.business.po;

import lombok.Data;

/**
 * @author zhangxiao
 * @since 2019年11月1日14:25:52
 */

@Data
public class TaskNodeVariatePO {

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
