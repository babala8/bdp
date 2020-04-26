package com.zjft.microservice.treasurybrain.timejob.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TimeJobSysOrgPO {
    private String no;

    private String name;

    private String parentOrg;

    private BigDecimal left;

    private BigDecimal right;

    private BigDecimal orgGradeNo;

    private String moneyorgFlag;

    private String x;

    private String y;

    private String address;

    private String linkman;

    private String telephone;

    private String mobile;

    private String fax;

    private String email;

    private String businessRange;

    private String cupAreaCode;

    private String addressCode;

    private String areaNo;

    private String areaType;

    private String orgPhysicsCatalog;

    private String note;

    private String clrCenterNo;

    private String city;

    private String region;

    private BigDecimal status;

    private BigDecimal clrCenterFlag;

    private String orgNo;

    private String shortName;

    private String fullName;

    private BigDecimal awayFlag;

    private String clrCenterNoCash;

    private String lineNo;
}
