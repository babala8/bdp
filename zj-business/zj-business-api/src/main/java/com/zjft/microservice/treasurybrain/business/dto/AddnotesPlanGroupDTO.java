package com.zjft.microservice.treasurybrain.business.dto;

import lombok.Data;

@Data
public class AddnotesPlanGroupDTO {
    private Integer planDevCnt;

    private Integer planNetpntCnt;

    private Integer planDistance;

    private Integer planTimeCost;

	private String addnotesPlanNo;

	private String groupNo;

	private Integer clrTimeInterval;

}
