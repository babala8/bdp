package com.zjft.microservice.treasurybrain.business.domain;

import lombok.Data;

@Data
public class AddnoteLine {

    private String lineNo;

    private String lineName;

    private String clrCenterNo;

    private Integer addClrPeriod;

    private String note;

    private  Integer lineType;
}
