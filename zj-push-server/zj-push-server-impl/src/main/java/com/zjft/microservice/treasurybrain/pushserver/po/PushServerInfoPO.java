package com.zjft.microservice.treasurybrain.pushserver.po;

import lombok.Data;

@Data
public class PushServerInfoPO {

	private String no;

    private String time;

    private String name;

    private String message;

    private Integer noticeWay;

    private String noticeCategory;

    private String noticeTitle;

    private String noticeAddress;

    private Integer noticeFlag;

    private String  noticeCategoryDescription;
}
