package com.zjft.microservice.treasurybrain.task.dto;

import lombok.Data;

@Data
public class TaskEntityTableInfoDTO {
	private String taskNo;

	private String entityNo;

    private String customerNo;

    private Integer entityType;

    private Integer direction;

    private String upperNo;

    private Integer leafFlag;

    private Integer status;

	private Integer taskType;

	//业务使用，无意义
	private int count;

}
