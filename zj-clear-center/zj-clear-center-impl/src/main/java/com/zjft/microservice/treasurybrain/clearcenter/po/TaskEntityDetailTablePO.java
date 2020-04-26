package com.zjft.microservice.treasurybrain.clearcenter.po;

import lombok.Data;

@Data
public class TaskEntityDetailTablePO {
    private String id;

    private String taskNo;

    private String containerNo;

    private Integer containerType;

    private double amount;

    private Integer currencyType;

    private String currencyCode;

    private Integer denomination;

    private Integer opType;

    private String opNo;

    private String opTime;

    private String clearMachineNo;

    //业务使用，无意义
    private int count;
}
