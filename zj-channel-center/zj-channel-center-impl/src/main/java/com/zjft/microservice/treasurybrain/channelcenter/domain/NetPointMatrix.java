package com.zjft.microservice.treasurybrain.channelcenter.domain;


import lombok.Data;

@Data
public class NetPointMatrix extends NetPointMatrixKey {

    private Integer distance;

    private Integer timeCost;

    private String note;

    private int count;
    
    private String clrCenterNo;

    private Integer dataType;


}
