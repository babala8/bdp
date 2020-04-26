package com.zjft.microservice.treasurybrain.task.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskDetailTablePO {
	private String id;

	private String taskNo;

    private String productNo;

    private Integer direction;

    private BigDecimal amount;


}
