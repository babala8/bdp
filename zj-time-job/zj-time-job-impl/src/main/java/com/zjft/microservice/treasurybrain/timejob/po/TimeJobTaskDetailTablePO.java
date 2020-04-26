package com.zjft.microservice.treasurybrain.timejob.po;

import lombok.Data;

@Data
public class TimeJobTaskDetailTablePO {

	private String taskNo;

	private String customerNo;

    private Integer customerType;

    private Integer sort;

    private String address;

    private String note;


}
