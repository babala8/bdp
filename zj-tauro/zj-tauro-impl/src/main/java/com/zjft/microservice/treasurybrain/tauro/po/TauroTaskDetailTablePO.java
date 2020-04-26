package com.zjft.microservice.treasurybrain.tauro.po;

import lombok.Data;

@Data
public class TauroTaskDetailTablePO {

	private String taskNo;

	private String customerNo;

    private Integer customerType;

    private Integer sort;

    private String address;

    private String note;

	private Integer status;
}
