package com.zjft.microservice.treasurybrain.timejob.po;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class TimeJobTaskInOutPO {
    private String id;

    private String taskNo;

    private String customerNo;

    private Integer direction;

    private Integer transferType;

    private Integer containerType;

    private BigDecimal amount;

    private Integer currencyType;

    private String currencyCode;

    private Integer denomination;


}
