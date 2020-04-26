package com.zjft.microservice.treasurybrain.tauro.po;

import lombok.Data;

@Data
public class TauroProductStatusConvertPO {
    private String id;

    private Integer serviceNo;

    private Integer curStatus;

    private String proServiceNo;

    private Integer nextStatus;

	private String operateType;

	private String description;

    private String implNo;

}
