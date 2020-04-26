package com.zjft.microservice.treasurybrain.clearcenter.po;

import lombok.Data;

@Data
public class TaskDetailTablePO {
	private String taskNo;

	private String customerNo;

    private Integer customerType;

    private Integer sort;

    private String address;

    private Integer status;

    private String note;
}
