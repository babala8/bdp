package com.zjft.microservice.treasurybrain.tauro.po;

import lombok.Data;

@Data
public class TauroTaskEntityTablePO {

	private String taskNo;

	private String containerNo;

	private String customerNo;

    private Integer entityType;

    private Integer direction;

    private String upperNo;

    private Integer leafFlag;

    private Integer status;

    private String note;


}
