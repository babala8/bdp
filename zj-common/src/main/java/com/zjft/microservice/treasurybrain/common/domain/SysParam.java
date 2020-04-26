package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

@Data
public class SysParam {

    private String logicId;

    private Integer catalog;

    private String paramName;

    private String paramValue;

    private String statement;

    private String description;

}
