package com.zjft.microservice.treasurybrain.task.dto;

import lombok.Data;

@Data
public class TaskDetailTableDTO {

	private String taskNo;

	private String customerNo;

	private Integer customerType;

	private Integer sort;

	private String address;

	private String note;
}
