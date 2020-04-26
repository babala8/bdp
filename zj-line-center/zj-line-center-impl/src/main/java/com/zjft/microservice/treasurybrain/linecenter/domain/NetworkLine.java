package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

@Data
public class NetworkLine {

    private String networkLineNo;

    private String networkLineName;

    private String clrCenterNo;

    private Integer addClrPeriod;

    private String note;

}
