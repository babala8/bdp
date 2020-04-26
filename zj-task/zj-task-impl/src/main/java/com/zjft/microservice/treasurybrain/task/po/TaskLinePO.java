package com.zjft.microservice.treasurybrain.task.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskLinePO {
    private String lineWorkId;

    private String taskNo;

    private BigDecimal sortNo;

    private String address;

    private String lng;

    private String lat;

    private String earlyTime;

    private String latestTime;

}
