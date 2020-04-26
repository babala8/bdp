package com.zjft.microservice.treasurybrain.tauro.po;

import lombok.Data;

@Data
public class TauroTaskPerRecorderPO {
    private String id;

    private String taskNo;

    private String containerNo;

    private String performTime;

    private Integer performType;

    private String opNo1;

    private String opNo2;

    private Integer containerType;

}
