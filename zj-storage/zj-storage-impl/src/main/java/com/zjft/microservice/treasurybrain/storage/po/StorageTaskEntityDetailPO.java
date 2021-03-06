package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StorageTaskEntityDetailPO {
    private String id;

    private String taskNo;

    private String containerNo;

    private Integer containerType;

    private BigDecimal amount;

    private Integer currencyType;

    private String currencyCode;

    private Integer denomination;

    private Integer opType;

    private String opNo;

    private String opTime;

    private String clearMachineNo;

}
