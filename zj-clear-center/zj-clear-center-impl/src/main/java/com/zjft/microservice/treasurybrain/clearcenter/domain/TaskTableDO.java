package com.zjft.microservice.treasurybrain.clearcenter.domain;

import com.zjft.microservice.treasurybrain.clearcenter.po.TaskTablePO;
import lombok.Data;

@Data
public class TaskTableDO extends TaskTablePO {
	//路线名称
	private String lineName;

	//金库名称
	private String centerName;

	//任务单加钞金额
	private double addnoteAmount;

	//申请人
	private String  createOpName;

    //机构名称
	private String  orgName;

}
